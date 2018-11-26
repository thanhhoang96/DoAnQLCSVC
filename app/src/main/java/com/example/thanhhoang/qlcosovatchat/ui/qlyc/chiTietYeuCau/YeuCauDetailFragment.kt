package com.example.thanhhoang.qlcosovatchat.ui.qlyc.chiTietYeuCau

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauDetail
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.popBackStackFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_yeu_cau_detail.*

class YeuCauDetailFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: YeuCauDetailViewModel? = null
    private var yeuCauDetailAdapter: YeuCauDetailAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_yeu_cau_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = arguments?.getString("yeuCauID")

        initData()
        loadData(id)
        handleListener()
    }

    private fun initData() {
        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        viewModel = YeuCauDetailViewModel(Repository())
    }

    private fun loadData(id: String?) {
        if (!id.isNullOrEmpty()) {
            dialog?.show()
            Handler().postDelayed({
                id?.let { it ->
                    viewModel?.getYeuCauDetail(it)
                            ?.subscribeOn(Schedulers.io())
                            ?.doFinally { dialog?.dismiss() }
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe({
                                updateData(it.data.proposal)
                            }, {})
                }
            }, 2000)
        }
    }

    private fun handleListener() {
        btnTroVeDetail.setOnClickListener {
            (activity as MainActivity).apply {
                popBackStackFragment()
                createYeuCauSuccess()
            }
        }
    }

    private fun updateData(yeuCauDetail: YeuCauDetail) {
        tvTieuDeYauCauDetail.text = yeuCauDetail.title
        tvMoTaYcDetail.text = yeuCauDetail.desc
        tvNgayTaoYcDetail.text = yeuCauDetail.ngayTaoYeuCauDetail
        tvDonViYcDetail.text = yeuCauDetail.unit.name
        tvTrangThaiYcDetail.text = if (yeuCauDetail.trangThaiYcDetail == 0) "Chưa duyệt" else if (yeuCauDetail.trangThaiYcDetail == 0) "Đã xác nhận" else "Đã duyệt"
        tvLoaiYeuCauYcDetail.text = yeuCauDetail.type.name
        yeuCauDetailAdapter = YeuCauDetailAdapter(yeuCauDetail.listYeuCauDetail)
        recyclerViewYcDetail.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = yeuCauDetailAdapter
        }
    }
}
