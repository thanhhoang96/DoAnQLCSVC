package com.example.thanhhoang.qlcosovatchat.ui.qlkh

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
import android.widget.LinearLayout
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.KeHoach
import com.example.thanhhoang.qlcosovatchat.data.response.KeHoachResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.addFragment
import com.example.thanhhoang.qlcosovatchat.extention.afterTextChanged
import com.example.thanhhoang.qlcosovatchat.ui.qlkh.chiTietKeHoach.KeHoachDetailFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_quan_li_ke_hoach.*
import kotlinx.android.synthetic.main.fragment_quan_li_yeu_cau.*

class QuanLiKeHoachFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: QuanLiKeHoachViewModel? = null
    private var keHoachList: MutableList<KeHoach>? = null
    private var keHoachAdapter: QuanLiKeHoachAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        return inflater.inflate(R.layout.fragment_quan_li_ke_hoach, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        loadData()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initView() {
        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        keHoachList = arrayListOf()
        keHoachAdapter = keHoachList?.let { QuanLiKeHoachAdapter(it) }
        recyclerViewQlkh.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = keHoachAdapter
        }
    }

    private fun loadData() {
        dialog?.show()
        viewModel = QuanLiKeHoachViewModel(Repository())
        Handler().postDelayed({
            viewModel?.getAllKeHoach()
                    ?.subscribeOn(Schedulers.io())
                    ?.doFinally { dialog?.dismiss() }
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        updateList(it)
                    }, {})
        }, 2000)
    }

    private fun handleListener() {
        imgClearInputQlkh.setOnClickListener {
            edtSearchQlkh.setText("")
        }

        edtSearchQlyc.afterTextChanged { _ ->
            searchKeHoach(spStateQlkh.selectedItem.toString(), edtSearchQlkh.text.toString())
        }
    }

    private fun handleListenerFromInterface() {
        keHoachAdapter?.apply {
            sentPositionXoaItemQlKh = {
                showDialogXoaKh(it)
            }

            sentPositionItemKhDtail = {
                val bundle = Bundle()
                bundle.putString("keHoachID", keHoachList?.get(it)?.id)
                val keHoachDetail = KeHoachDetailFragment()
                keHoachDetail.arguments = bundle
                (activity as MainActivity).addFragment(R.id.flContainer, keHoachDetail)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun searchKeHoach(trangThai: String, tenKeHoach: String) {
        val msg = if (tenKeHoach.isEmpty()) null else tenKeHoach
        val status = when (trangThai) {
            "Tất cả" -> null
            "Chưa duyệt" -> 0
            "Đã xác nhận" -> 1
            else -> 2
        }
        viewModel?.searchKeHoach(status, msg)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    updateList(it)
                }, {})
    }

    private fun updateList(responseData: KeHoachResponse) {
        keHoachList?.apply {
            clear()
            addAll(responseData.data.keHoachList)
        }
        keHoachAdapter?.notifyDataSetChanged()
        recyclerViewQlkh.scrollToPosition(0)
    }

    private fun showDialogXoaKh(position: Int) {
        val dialogBuilder = AlertDialog.Builder(activity as MainActivity)
        dialogBuilder.setTitle("Xoá kế hoạch")
        dialogBuilder.setMessage("Bạn có chắc muốn xoá yêu cầu [${keHoachList?.get(position)?.tieuDeKeHoach}] này không?")
        dialogBuilder.setPositiveButton("Ok") { dialog, _ ->
            //todo xoa ke hoach
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Huỷ") { dialog, _ ->
            dialog.dismiss()
        }

        val dialogConfirm = dialogBuilder.create()
        dialogConfirm.show()
    }
}