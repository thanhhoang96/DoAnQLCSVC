package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
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
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.ItemKhDetail
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Infra
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemProposalRequest
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.PlanForYeuCauDetail
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauMuaSamRequest
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.popBackStackFragment
import com.example.thanhhoang.qlcosovatchat.extention.showDialog
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import com.example.thanhhoang.qlcosovatchat.util.Helpers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_show_plan_detail_for_ycms.view.*
import kotlinx.android.synthetic.main.fragment_tao_moi_yc.*

class TaoMoiYeuCauFragment : Fragment() {
    private var dialog: Dialog? = null

    private var viewModel: TaoMoiYeuCauViewModel? = null
    private var suaChuaAdapter: TaoMoiSuaChuaAdapter? = null
    private var muaSamAdapter: TaoMoiMuaSamAdapter? = null
    private var dialogPlanAdapter: DialogPlanAdapter? = null

    private var taiSanIdList: MutableList<String> = mutableListOf()
    private var keHoachNameList: MutableList<String> = mutableListOf()
    private var suaChuaList: MutableList<Infra> = mutableListOf()
    private var keHoachList: MutableList<PlanForYeuCauDetail> = mutableListOf()
    private var dialogPlanList: MutableList<ItemKhDetail> = mutableListOf()
    private var muaSamList: MutableList<ItemKhDetail> = mutableListOf()

    private var isTaoMoiSuaChua = false
    private var positionPlanSelect = 0

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
        handleInterface()
    }

    private fun initView() {
        (activity as MainActivity).setTitleMenu("Tạo mới yêu cầu")

        viewModel = TaoMoiYeuCauViewModel(Repository())

        muaSamAdapter = TaoMoiMuaSamAdapter(muaSamList)
        recyclerViewTaoMoiYCMS?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = muaSamAdapter
        }
    }

    private fun handleListener() {
        rgYeuCau.setOnCheckedChangeListener { _, checkedId ->
            edtTieuDeTaoMoiYc.setText("")
            edtGiaiTrinhTaoMoiYc.setText("")

            if (checkedId == rbMuaSam.id) {
                isTaoMoiSuaChua = false
                llThemThietBiMuaSamYc.visibility = View.VISIBLE
                llAllTruongTaoMoiMuaSam.visibility = View.VISIBLE
                recyclerViewTaoMoiYCMS.visibility = View.VISIBLE
                llAllTruongTaoMoiSuaChuaYc.visibility = View.GONE
                recyclerViewTaoMoiYCSC.visibility = View.GONE

            } else {
                isTaoMoiSuaChua = true
                llThemThietBiMuaSamYc.visibility = View.GONE
                llAllTruongTaoMoiMuaSam.visibility = View.GONE
                recyclerViewTaoMoiYCMS.visibility = View.GONE
                llAllTruongTaoMoiSuaChuaYc.visibility = View.VISIBLE
                recyclerViewTaoMoiYCSC.visibility = View.VISIBLE
                loadDataSuaChua()
            }
        }

        btnTheoKeHoachAddYc.setOnClickListener {
            showDialogPlanDetail()
        }

        btHuyYc.setOnClickListener {
            (activity as MainActivity).popBackStackFragment()
        }

        btnResetYc.setOnClickListener {
            edtTieuDeTaoMoiYc.setText("")
            edtGiaiTrinhTaoMoiYc.setText("")
            loadDataSuaChua()
        }

        btnTaoGuiYc.setOnClickListener { _ ->
            if (edtTieuDeTaoMoiYc.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Mời bạn nhập tiêu đề trước khi tạo yêu cầu", Toast.LENGTH_SHORT).show()
            } else {
                if (isTaoMoiSuaChua) {
                    if (taiSanIdList.size == 0) {
                        Toast.makeText(activity, "Bạn không thể tạo yêu cầu sửa chữa", Toast.LENGTH_SHORT).show()
                    } else {
                        val yeuCauSuaChuaRequest = YeuCauSuaChuaRequest(if (edtGiaiTrinhTaoMoiYc.text.isNullOrEmpty()) null else edtGiaiTrinhTaoMoiYc.text.toString(),
                                edtTieuDeTaoMoiYc.text.toString(), taiSanIdList)
                        dialog?.show()

                        Handler().postDelayed({
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
                        }, 1500)
                    }
                } else {
                    if (edtGiaiTrinhTaoMoiYc.text.isNullOrEmpty()) {
                        Toast.makeText(activity, "Mời bạn nhập thông tin giải trình trước khi tạo yêu cầu", Toast.LENGTH_SHORT).show()
                    } else {
                        if (muaSamList.size == 0) {
                            Toast.makeText(activity, "Bạn không thể tạo yêu cầu mua sắm", Toast.LENGTH_SHORT).show()
                        } else {
                            dialog?.show()
                            val listData: MutableList<ItemProposalRequest> = mutableListOf()
                            muaSamList.forEach {
                                listData.add(ItemProposalRequest(it.detailPlanId, keHoachList[positionPlanSelect].planId, it.soLuongConLai))
                            }
                            val yeuCauMuaSamRequest = YeuCauMuaSamRequest(edtTieuDeTaoMoiYc.text.toString(), edtGiaiTrinhTaoMoiYc.text.toString(), listData)

                            Handler().postDelayed({
                                viewModel?.createYeuCauMuaSam(yeuCauMuaSamRequest)
                                        ?.subscribeOn(Schedulers.io())
                                        ?.doFinally { dialog?.dismiss() }
                                        ?.observeOn(AndroidSchedulers.mainThread())
                                        ?.subscribe({
                                            if (it != null) {
                                                Toast.makeText(activity, "Tạo yêu cầu mua sắm thành công", Toast.LENGTH_SHORT).show()
                                                (activity as MainActivity).apply {
                                                    popBackStackFragment()
                                                    createYeuCauSuccess()
                                                }
                                            }
                                        }, { Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show() })
                            }, 1500)
                        }
                    }
                }
            }
        }

        llTaoMoiYeuCau.setOnClickListener {
            Helpers.hideSoftKeyboard(activity as MainActivity)
        }
    }

    private fun handleInterface() {
        (activity as MainActivity).sentYeuCauFromDialog = {
            muaSamAdapter?.notifyDataSetChanged()
        }

        muaSamAdapter?.apply {
            sentPositionXoaItemYcms = {
                (activity as MainActivity).showDialog("Bạn có chắc muốn xoá dữ kiệu này không?", "Ok", "Huỷ",
                        DialogInterface.OnClickListener { _, _ ->
                            muaSamList.removeAt(it)
                            this.notifyDataSetChanged()
                        })

            }

            tangSoLuongYcms = {
                val soLuong = muaSamList[it].soLuongThayDoi
                muaSamList[it].soLuongThayDoi = if (soLuong < muaSamList[it].soLuongConLai) soLuong.plus(1) else muaSamList[it].soLuongConLai
                this.notifyDataSetChanged()
            }

            giamSoLuongYcms = {
                val soLuong = muaSamList[it].soLuongThayDoi
                muaSamList[it].soLuongThayDoi = if (soLuong > 0) soLuong.minus(1) else 0
                this.notifyDataSetChanged()
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
                        suaChuaList.clear()
                        suaChuaList.addAll(it.data.taiSanHuHongList)
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
                taiSanIdList.add(suaChuaList[it].id)
            }

            sentPositionTaiSanHuHongNotCheck = {
                taiSanIdList.remove(suaChuaList[it].id)
            }
        }
    }

    @SuppressLint("CheckResult", "InflateParams")
    private fun showDialogPlanDetail() {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_show_plan_detail_for_ycms, null)

        val mBuilder = context?.let {
            AlertDialog.Builder(it)
                    .setView(mDialogView)
        }
        val mAlertDialog = mBuilder?.show()

        viewModel?.getPlanTypeForYeuCauMuaSam()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ response ->
                    keHoachList.clear()
                    keHoachNameList.clear()
                    response.data.plans.forEach {
                        keHoachList.add(it)
                        keHoachNameList.add(it.name)
                    }
                    val adapterPlanForYc = ArrayAdapter<String>(activity as MainActivity, android.R.layout.simple_spinner_item, keHoachNameList)
                    adapterPlanForYc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mDialogView.spKeHoachDetaiForYc?.adapter = adapterPlanForYc
                }, {})

        mDialogView.spKeHoachDetaiForYc?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionPlanSelect = position
                viewModel?.getDetailPlanForYcms(keHoachList[position].planId)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            dialogPlanList.apply {
                                clear()
                                addAll(it.data.listPlanDetailForYc)
                                forEach {
                                    it.soLuongThayDoi = it.soLuongConLai
                                }
                            }

                            dialogPlanAdapter = DialogPlanAdapter(dialogPlanList)
                            mDialogView.recyclerViewYeuCauTmDetail?.apply {
                                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                                adapter = dialogPlanAdapter
                            }

                            handleListenerFromDialogPlan()
                        }, {})
            }
        }

        mDialogView.btnLuuYeuCauMsDetail.setOnClickListener {
            (activity as MainActivity).sentYeuCauFromDialog()
            mAlertDialog?.dismiss()
        }

        mDialogView.btnHuyYeuCauMsDetail.setOnClickListener {
            mAlertDialog?.dismiss()
        }
    }

    private fun handleListenerFromDialogPlan() {
        dialogPlanAdapter?.apply {
            sentPositionPlanDetailIsCheck = {
                muaSamList.add(dialogPlanList[it])
            }

            sentPositionPlanDetailNotCheck = {
                muaSamList.remove(dialogPlanList[it])
            }
        }
    }
}
