package com.example.thanhhoang.qlcosovatchat.ui.qlts

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
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Infra
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.afterTextChanged
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_change_state_qlts.view.*
import kotlinx.android.synthetic.main.fragment_quan_li_tai_san.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class QuanLiTaiSanFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: QuanLiTaiSanViewModel? = null
    private var taiSanList: MutableList<Infra>? = null
    private var taiSanAdapter: QuanLiTaiSanAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        return inflater.inflate(R.layout.fragment_quan_li_tai_san, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initView()
        loadData()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initView() {
        taiSanList = arrayListOf()
        taiSanAdapter = QuanLiTaiSanAdapter(taiSanList as ArrayList<Infra>)
        recyclerViewQlts.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = taiSanAdapter
        }
    }

    @SuppressLint("CheckResult")
    private fun loadData() {
        dialog?.show()
        viewModel = QuanLiTaiSanViewModel(Repository())
        Handler().postDelayed({
            viewModel?.taiSanList()
                    ?.subscribeOn(Schedulers.io())
                    ?.doFinally { dialog?.dismiss() }
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        updateList(it)
                    }, {})
        }, 2000)
    }

    private fun handleListener() {
        imgClearInputQlts.setOnClickListener {
            edtSearchQlts.setText("")
        }

        edtSearchQlts.afterTextChanged { _ ->
            searchApi(spStateQlts.selectedItem.toString(), edtSearchQlts.text.toString())
        }

        spStateQlts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                searchApi(spStateQlts.selectedItem.toString(), edtSearchQlts.text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun handleListenerFromInterface() {
        taiSanAdapter?.sentPositionItemQlts = { it ->
            showDialogChangeState(it)
        }
    }

    @SuppressLint("CheckResult")
    private fun searchApi(state: String, maDinhdanh: String) {
        val msg = if (maDinhdanh.isEmpty()) null else maDinhdanh
        val status = when (state) {
            "Tất cả" -> null
            "Hư hỏng" -> "HH"
            "Đang sử dụng" -> "DSD"
            else -> "DSC"
        }
        viewModel?.searchTaiSan(status, msg)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    updateList(it)
                }, {})
    }

    @SuppressLint("InflateParams")
    private fun showDialogChangeState(position: Int) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_change_state_qlts, null)

        val mBuilder = context?.let {
            AlertDialog.Builder(it)
                    .setView(mDialogView)
                    .setTitle("Trạng thái")
        }
        val mAlertDialog = mBuilder?.show()

        when {
            taiSanList?.get(position)?.unitEquipmentStatus == "DSD" -> mDialogView.rbDangSuDungState.isChecked = true
            taiSanList?.get(position)?.unitEquipmentStatus == "HH" -> mDialogView.rbHuHongState.isChecked = true
        }

        // handle Save data when click button Luu
        mDialogView.btnLuu.setOnClickListener {
            taiSanList?.get(position)?.unitEquipmentStatus =
                    if (mDialogView.rbHuHongState.isChecked) "HH" else "DSD"
            taiSanAdapter?.notifyDataSetChanged()
            mAlertDialog?.dismiss()
        }

        // close dialog when click button huy
        mDialogView.btnHuy.setOnClickListener {
            mAlertDialog?.dismiss()
        }

        // handle dialog dismiss
        mAlertDialog?.setOnDismissListener { _ ->
            viewModel?.changeStatusTaiSan(EquipmentId(taiSanList?.get(position)?.id.toString()))
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        searchApi(spStateQlts.selectedItem.toString(), edtSearchQlts.text.toString())
                    }, {})
        }
    }

    private fun updateList(responseData: TaiSanResponse) {
        taiSanList?.apply {
            clear()
            addAll(responseData.data.taiSanList)
        }

        taiSanAdapter?.notifyDataSetChanged()
        recyclerViewQlts.scrollToPosition(0)

        tvTaiSanNotFound.visibility = if (taiSanList?.size == 0) View.VISIBLE else View.GONE
    }
}
