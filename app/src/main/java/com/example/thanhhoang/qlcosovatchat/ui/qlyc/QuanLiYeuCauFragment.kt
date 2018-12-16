package com.example.thanhhoang.qlcosovatchat.ui.qlyc

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCau
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.addFragment
import com.example.thanhhoang.qlcosovatchat.extention.afterTextChanged
import com.example.thanhhoang.qlcosovatchat.ui.qlyc.chiTietYeuCau.YeuCauDetailFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlyc.suaChuaYeuCau.SuaChuaYeuCauFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi.TaoMoiYeuCauFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_quan_li_yeu_cau.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class QuanLiYeuCauFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: QuanLiYeuCauViewModel? = null
    private var yeuCauList: MutableList<YeuCau> = mutableListOf()
    private var yeuCauAdapter: QuanLiYeuCauAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        return inflater.inflate(R.layout.fragment_quan_li_yeu_cau, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        loadData()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initView() {
        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        yeuCauAdapter = QuanLiYeuCauAdapter(yeuCauList)
        recyclerViewListQlyc.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = yeuCauAdapter
        }
    }

    private fun loadData() {
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
            searchYeuCau(spStateQlyc.selectedItem.toString(), edtSearchQlyc.text.toString())
        }

        spStateQlyc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                searchYeuCau(spStateQlyc.selectedItem.toString(), edtSearchQlyc.text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    @SuppressLint("CheckResult")
    private fun searchYeuCau(state: String, tieuDe: String) {
        val msg = if (tieuDe.isEmpty()) null else tieuDe
        val status = when (state) {
            "Tất cả" -> null
            "Chưa duyệt" -> 0
            "Đã xác nhận" -> 1
            else -> 2
        }
        viewModel?.searchYeuCau(status, msg)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    updateList(it)
                }, {})
    }

    private fun handleListenerFromInterface() {
        yeuCauAdapter?.apply {
            sentPositionItemSuaYeuCau = {
                val bundle = Bundle()
                bundle.putString("yeuCauSuaChuaId", yeuCauList[it].idYC)
                val suaChuaYeuCauFragment = SuaChuaYeuCauFragment()
                suaChuaYeuCauFragment.arguments = bundle
                (activity as MainActivity).addFragment(R.id.flContainer, suaChuaYeuCauFragment)
            }

            sentPositionItemXoaYeuCau = {
                showDialogXoaYc(it)
            }

            sentPositionGetYeuCauDetail = {
                val bundle = Bundle()
                bundle.apply {
                    putString("yeuCauID", yeuCauList[it].idYC)
                    putBoolean("ycType", yeuCauList[it].type.name == "Yêu cầu mua sắm")
                }
                val yeuCauDetailFragment = YeuCauDetailFragment()
                yeuCauDetailFragment.arguments = bundle
                (activity as MainActivity).addFragment(R.id.flContainer, yeuCauDetailFragment)
            }
        }

        (activity as MainActivity).createYeuCauSuccess = {
            searchYeuCau(spStateQlyc.selectedItem.toString(), edtSearchQlyc.text.toString())
        }
    }

    private fun updateList(responseData: YeuCauResponse) {
        yeuCauList.apply {
            clear()
            addAll(responseData.data.yeuCauList)
        }

        yeuCauAdapter?.notifyDataSetChanged()
        recyclerViewListQlyc?.scrollToPosition(0)

        tvYeuCauNotFound.visibility = if (yeuCauList.size == 0) View.VISIBLE else View.VISIBLE
    }

    private fun showDialogXoaYc(position: Int) {
        val dialogBuilder = AlertDialog.Builder(activity as MainActivity)
        dialogBuilder.setTitle("Xoá yêu cầu")
        dialogBuilder.setMessage("Bạn có chắc muốn xoá yêu cầu [${yeuCauList[position].tieuDeYC}] này không?")
        dialogBuilder.setPositiveButton("Ok") { dialog, _ ->
            yeuCauList[position].idYC.let { it ->
                viewModel?.deleteYeuCau(it)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            searchYeuCau(spStateQlyc.selectedItem.toString(), edtSearchQlyc.text.toString())
                            Toast.makeText(activity, "Xoá thành công", Toast.LENGTH_SHORT).show()
                        }, {})
            }
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Huỷ") { dialog, _ ->
            dialog.dismiss()
        }

        val dialogConfirm = dialogBuilder.create()
        dialogConfirm.show()
    }
}
