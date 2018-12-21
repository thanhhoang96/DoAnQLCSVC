package com.example.thanhhoang.qlcosovatchat.ui.qlkh.chiTietKeHoach

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
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.KeHoachDetail
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.popBackStackFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import com.example.thanhhoang.qlcosovatchat.util.FormatUtils
import com.example.thanhhoang.qlcosovatchat.util.Helpers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_ke_hoach_detail.*

class KeHoachDetailFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: KeHoachDetailViewModel? = null
    private var keHoachDetailAdapter: KeHoachDetailAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ke_hoach_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = arguments?.getString("keHoachID")

        initData()
        loadData(id)
        handleListener()
    }

    private fun initData() {
        (activity as MainActivity).setTitleMenu("Chi tiết kế hoạch")

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        viewModel = KeHoachDetailViewModel(Repository())
    }

    private fun loadData(id: String?) {
        if (!id.isNullOrEmpty()) {
            dialog?.show()
            Handler().postDelayed({
                id?.let { it ->
                    viewModel?.getKeHoachDetail(it)
                            ?.subscribeOn(Schedulers.io())
                            ?.doFinally { dialog?.dismiss() }
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe({
                                updateData(it.data.plan)
                            }, {})
                }
            }, 2000)
        }
    }

    private fun handleListener() {
        btnTroVeKhDetail.setOnClickListener {
            (activity as MainActivity).apply {
                popBackStackFragment()
                createKeHoachSuccesListener()
            }
        }

        llKeHoachDetail.setOnClickListener {
            Helpers.hideSoftKeyboard(activity as MainActivity)
        }
    }

    private fun updateData(keHoachDetail: KeHoachDetail) {
        tvTieuDeKeHoachDetail.text = keHoachDetail.title
        tvMoTaKhDetail.text = keHoachDetail.desc
        tvNgayTaoKhDetail.text = FormatUtils.formatDisplayDate(keHoachDetail.ngayTaoKh)
        tvDonViKhDetail.text = keHoachDetail.unit.name
        tvTrangThaiKhDetail.text = if (keHoachDetail.trangThaiKhDetail == 0) "Chưa duyệt" else if (keHoachDetail.trangThaiKhDetail == 0) "Đã xác nhận" else "Đã duyệt"
        tvLoaiYeuCauKhDetail.text = keHoachDetail.type.name
        keHoachDetailAdapter = KeHoachDetailAdapter(keHoachDetail.itemKhList)
        recyclerViewKhDetail.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = keHoachDetailAdapter
        }
    }
}
