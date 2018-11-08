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

class QuanLiTaiSanFragment : Fragment() {
    private var dialog: Dialog? = null

    private var viewModel: QuanLiTaiSanViewModel? = null
    private var taiSanList: MutableList<Infra>? = null
    private var taiSanAdapter: QuanLiTaiSanAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quan_li_tai_san, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initView()
        connApi()
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
    private fun connApi() {
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
            val msg = edtSearchQlts.text.toString()
            val status = if (spState.selectedItem.toString() == "Hu hong") "HH" else
                (if (spState.selectedItem.toString() == "Dang su dung") "DSD" else "DSC")
            if (msg.isEmpty()) {
                viewModel?.searchTaiSan(status, null)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            updateList(it)
                        }, {})
            } else {
                viewModel?.searchTaiSan(spState.selectedItem.toString(), msg)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            updateList(it)
                        }, {})
            }
        }
    }

    private fun handleListenerFromInterface() {
        taiSanAdapter?.sentPositionItemQlts = { it ->
            showDialogChangeState(it)
        }
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
                    ?.subscribe({}, {})
        }
    }

    private fun updateList(taiSan: TaiSanResponse) {
        taiSanList?.apply {
            clear()
            addAll(taiSan.data.taiSanList)
        }
        taiSanAdapter?.notifyDataSetChanged()
    }
}
