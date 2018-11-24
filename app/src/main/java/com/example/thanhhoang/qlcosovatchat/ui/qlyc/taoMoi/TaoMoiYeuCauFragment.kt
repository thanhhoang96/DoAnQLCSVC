package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Infra
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCau
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.popBackStackFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_tao_moi_yc.*

class TaoMoiYeuCauFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: TaoMoiYeuCauViewModel? = null
    private var suaChuaList: MutableList<Infra>? = null
    private var muaSamList: MutableList<YeuCau>? = null
    private var suaChuaAdapter: TaoMoiSuaChuaAdapter? = null
    private var muaSamAdapter: TaoMoiMuaSamAdapter? = null
    private var taiSanIdList: MutableList<String>? = null

    private var isTaoMoiSuaChua = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        return inflater.inflate(R.layout.fragment_tao_moi_yc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initView()
        handleListener()
    }

    private fun initView() {
        suaChuaList = arrayListOf()
        muaSamList = arrayListOf()
        taiSanIdList = arrayListOf()
        viewModel = TaoMoiYeuCauViewModel(Repository())
    }

    private fun handleListener() {
        rgYeuCau.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == rbMuaSam.id) {
                isTaoMoiSuaChua = false
            } else {
                isTaoMoiSuaChua = true
                llThemThietBiMuaSam.visibility = View.GONE
                llAllTruongTaoMoiMuaSam.visibility = View.GONE
                recyclerViewTaoMoiYCMS.visibility = View.GONE
                llAllTruongTaoMoiSuaChua.visibility = View.VISIBLE
                recyclerViewTaoMoiYCSC.visibility = View.VISIBLE
                loadDataSuaChua()
            }
        }

        btHuyYc.setOnClickListener {
            (activity as MainActivity).popBackStackFragment()
        }

        btnResetYc.setOnClickListener {
            edtTieuDeTaoMoiYc.setText("")
            edtGiaiTrinhYc.setText("")
            loadDataSuaChua()
        }

        btnTaoGuiYc.setOnClickListener { _ ->
            if (edtTieuDeTaoMoiYc.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Mời bạn nhập tiêu đề trước khi tạo yêu cầu", Toast.LENGTH_SHORT).show()
            } else {
                if (taiSanIdList?.size == 0) {
                    Toast.makeText(activity, "Bạn không thể tạo yêu cầu sửa chữa", Toast.LENGTH_SHORT).show()
                } else {
                    val yeuCauSuaChuaRequest = YeuCauSuaChuaRequest(if (edtGiaiTrinhYc.text.isNullOrEmpty()) null else edtGiaiTrinhYc.text.toString(),
                            edtTieuDeTaoMoiYc.text.toString(), taiSanIdList)
                    dialog?.show()
                    viewModel?.createYeuCauSuaChua(yeuCauSuaChuaRequest)
                            ?.subscribeOn(Schedulers.io())
                            ?.doFinally { dialog?.dismiss() }
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe({
                                if (it != null) {
                                    Toast.makeText(activity, "Tạo yêu cầu sửa chữa thành công", Toast.LENGTH_SHORT).show()
                                    (activity as MainActivity).apply {
                                        popBackStackFragment()
                                        createYeuCauSuccess()
                                    }

                                }
                            }, { handleTaoYeuCauFailed() })
                }
            }
        }
    }

    private fun handleTaoYeuCauFailed() {
        Toast.makeText(activity, "Tạo yêu cầu sửa chữa failed", Toast.LENGTH_SHORT).show()
    }

    private fun loadDataSuaChua() {
        dialog?.show()
        Handler().postDelayed({
            viewModel?.getAllTaiSanHuHong()
                    ?.subscribeOn(Schedulers.io())
                    ?.doFinally { dialog?.dismiss() }
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        suaChuaList?.clear()
                        suaChuaList?.addAll(it.data.taiSanHuHongList)
                        bindDataOnRecyclerView(suaChuaList)
                    }, {})
        }, 2000)
    }

    private fun bindDataOnRecyclerView(list: MutableList<Infra>?) {
        suaChuaAdapter = list?.let { TaoMoiSuaChuaAdapter(it) }
        recyclerViewTaoMoiYCSC.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = suaChuaAdapter
        }

        handleListenerFromSuaChuaAdapter()
    }

    private fun handleListenerFromSuaChuaAdapter() {
        suaChuaAdapter?.apply {
            sentPositionTaiSanHuHongIsCheck = {
                suaChuaList?.get(it)?.id?.let { it1 -> taiSanIdList?.add(it1) }
            }

            sentPositionTaiSanHuHongNotCheck = {
                suaChuaList?.get(it)?.id?.let { it1 -> taiSanIdList?.remove(it1) }
            }
        }
    }
}
