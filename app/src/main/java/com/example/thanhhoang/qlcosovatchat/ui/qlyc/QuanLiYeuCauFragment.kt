package com.example.thanhhoang.qlcosovatchat.ui.qlyc

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCau
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.addFragment
import com.example.thanhhoang.qlcosovatchat.extention.afterTextChanged
import com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi.TaoMoiYeuCauFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_quan_li_yeu_cau.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class QuanLiYeuCauFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: QuanLiYeuCauViewModel? = null
    private var yeuCauList: MutableList<YeuCau>? = null
    private var yeuCauAdapter: QuanLiYeuCauAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quan_li_yeu_cau, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initView()
        connApi()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initView() {
        yeuCauList = arrayListOf()
        yeuCauAdapter = yeuCauList?.let { QuanLiYeuCauAdapter(it) }
        recyclerViewListQlyc.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = yeuCauAdapter
        }
    }

    private fun connApi() {
        dialog?.show()
        viewModel = QuanLiYeuCauViewModel(Repository())
        Handler().postDelayed({
            viewModel?.getAllYeuCau()
                    ?.subscribeOn(Schedulers.io())
                    ?.doFinally { dialog?.dismiss() }
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        updateList(it)
                    }, {})
        }, 2000)
    }

    private fun handleListener() {
        btnTaoMoiYc.setOnClickListener {
            activity?.addFragment(R.id.flContainer, TaoMoiYeuCauFragment())
        }

        imgClearInputQlyc.setOnClickListener {
            edtSearchQlyc.setText("")
        }

        edtSearchQlyc.afterTextChanged { _ ->
            val msg = edtSearchQlyc.text.toString()
            val status = if (spStateQlyc.selectedItem.toString() == "Chua duyet") 0 else
                (if (spStateQlyc.selectedItem.toString() == "Da xac nhan") 1 else 2)
            if (msg.isEmpty()) {
                viewModel?.searchYeuCau(status, null)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            updateList(it)
                        }, {})
            } else {
                viewModel?.searchYeuCau(status, msg)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            updateList(it)
                        }, {})
            }
        }
    }

    private fun handleListenerFromInterface() {
        yeuCauAdapter?.sentPositionItemSuaYeuCau = {

        }

        yeuCauAdapter?.sentPositionItemXoaYeuCau = {
            showDialogXoaYc(it)
        }
    }

    private fun updateList(responseData: YeuCauResponse) {
        yeuCauList?.apply {
            clear()
            addAll(responseData.data.yeuCauList)
        }
        yeuCauAdapter?.notifyDataSetChanged()
    }

    private fun showDialogXoaYc(position: Int) {
        val dialogBuilder = AlertDialog.Builder(activity as MainActivity)
        dialogBuilder.setTitle("Xoá yêu cầu")
        dialogBuilder.setMessage("Bạn có chắc muốn xoá yêu cầu ${yeuCauList?.get(position)?.tieuDeYC} này không?")
        dialogBuilder.setPositiveButton("Ok") { dialog, _ ->
            yeuCauList?.remove(yeuCauList?.get(position))
            yeuCauAdapter?.notifyDataSetChanged()
            dialog.dismiss()

        }
        dialogBuilder.setNegativeButton("Huỷ") { dialog, _ ->
            dialog.dismiss()
        }
    }
}
