package com.example.thanhhoang.qlcosovatchat.ui.qlts

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.TaiSan
import com.example.thanhhoang.qlcosovatchat.extention.afterTextChanged
import kotlinx.android.synthetic.main.dialog_change_state_qlts.view.*
import kotlinx.android.synthetic.main.fragment_quan_li_tai_san.*

class QuanLiTaiSanFragment : Fragment() {
    private var taiSanList: MutableList<TaiSan>? = null
    private var taiSanAdapter: QuanLiTaiSanAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quan_li_tai_san, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initView()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initData() {
        taiSanList = arrayListOf()
        taiSanList?.add(TaiSan("100_123_5345", "Dell", "May tinh", "Dang su dung"))
        taiSanList?.add(TaiSan("100_123_5346", "Vaio", "May tinh", "Hu hong"))
        taiSanList?.add(TaiSan("100_123_5347", "cp001", "May in", "Hu hong"))
        taiSanList?.add(TaiSan("100_123_5348", "mac", "May tinh", "Dang su dung"))
        taiSanList?.add(TaiSan("100_123_5349", "Dell", "May tinh", "Dang su dung"))
        taiSanList?.add(TaiSan("100_123_5350", "Vaio", "May tinh", "Hu hong"))
        taiSanList?.add(TaiSan("100_123_5351", "Dell", "May tinh", "Dang su dung"))
        taiSanList?.add(TaiSan("100_123_5352", "Vaio", "May tinh", "Hu hong"))
    }

    private fun initView() {
        taiSanAdapter = taiSanList?.let { QuanLiTaiSanAdapter(it) }
        recyclerViewQlts.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = taiSanAdapter
        }
    }

    private fun handleListener() {
        imgClearInputQlts.setOnClickListener {
            edtSearchQlts.setText("")
        }

        edtSearchQlts.afterTextChanged {

        }
    }

    private fun handleListenerFromInterface() {
        taiSanAdapter?.sentPositionItemQlts = {
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

        if (taiSanList?.get(position)?.trangThai == "Dang su dung")
            mDialogView.rbDangSuDungState.isChecked = true
        else
            mDialogView.rbHuHongState.isChecked = true

        mDialogView.btnLuu.setOnClickListener {
            taiSanList?.get(position)?.trangThai = if (mDialogView.rbHuHongState.isChecked)
                mDialogView.rbHuHongState.text.toString() else mDialogView.rbDangSuDungState.text.toString()
            taiSanAdapter?.notifyDataSetChanged()
            mAlertDialog?.dismiss()
        }

        mDialogView.btnHuy.setOnClickListener {
            mAlertDialog?.dismiss()
        }
    }
}
