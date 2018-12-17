package com.example.thanhhoang.qlcosovatchat.ui.qlyc.suaChuaYeuCau

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemYcDetail
import com.example.thanhhoang.qlcosovatchat.data.response.SuaChuaYeuCauRequest
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.popBackStackFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sua_chua_yeu_cau.*

class SuaChuaYeuCauFragment : Fragment() {
    private var dialog: Dialog? = null
    private lateinit var viewModel: SuaChuaYeuCauViewModel

    private var muaSamAdapter: SuaChuaYeuCauMsAdapter? = null
    private var suaChuaAdapter: SuaChuaYeuCauScAdapter? = null

    private var thietBiList: MutableList<ItemYcDetail?> = mutableListOf()
    private var suaChuaList: MutableList<ItemYcDetail?> = mutableListOf()
    private var muaSamList: MutableList<ItemYcDetail?> = mutableListOf()

    private var yeuCauId: String? = null
    private var isMuaSam = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = SuaChuaYeuCauViewModel(Repository())
        return inflater.inflate(R.layout.fragment_sua_chua_yeu_cau, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yeuCauId = arguments?.getString("yeuCauSuaChuaId")
        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initView()
        handleListener()
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        dialog?.show()
        Handler().postDelayed({
            yeuCauId?.let { it ->
                viewModel.getYeuCauDetail(it)
                        .subscribeOn(Schedulers.io())
                        ?.doFinally { dialog?.dismiss() }
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            thietBiList = it.data.proposal.listYeuCauDetail

                            suaChuaList.clear()
                            muaSamList.clear()

                            if (it.data.proposal.type.name == "Yêu cầu sửa chữa") {
                                isMuaSam = false
                                suaChuaList.addAll(thietBiList)
                                llSuaChuaYeuCauMuaSam.visibility = View.GONE
                                llSuaChuaYeuCauSuaChua.visibility = View.VISIBLE
                                suaChuaAdapter = SuaChuaYeuCauScAdapter(suaChuaList)
                                recyclerViewYeuCauSuaChua.apply {
                                    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                                    adapter = suaChuaAdapter
                                }
                            } else {
                                isMuaSam = false
                                muaSamList.addAll(thietBiList)
                                llSuaChuaYeuCauMuaSam.visibility = View.VISIBLE
                                llSuaChuaYeuCauSuaChua.visibility = View.GONE
                                muaSamAdapter = SuaChuaYeuCauMsAdapter(muaSamList)
                                recyclerViewYeuCauSuaChua.apply {
                                    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                                    adapter = muaSamAdapter
                                }
                            }

                            edtTieuDeSuaChuaYeuCau.setText(it.data.proposal.title)
                            edtGiaiTrinhSuaChuaYeuCau.setText(it.data.proposal.desc)

                            handleInterface()
                        }, {})
            }
        }, 1000)
    }

    private fun handleListener() {
        btnTroVeSuaChuaYeuCau.setOnClickListener {
            (activity as MainActivity).popBackStackFragment()
        }

        btnSuaChuaYeuCau.setOnClickListener { _ ->
            if (edtTieuDeSuaChuaYeuCau.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Trường 'Tiêu đề' không được bỏ trống!", Toast.LENGTH_SHORT).show()
            } else {
                if (isMuaSam) {
                    dialog?.show()
                    val muaSamListRequest: MutableList<ItemSuaChuaRequest> = mutableListOf()
                    muaSamList.forEach {
                        it?.itemProposalId?.let { it1 -> ItemSuaChuaRequest(it1, it.soLuongYeuCau) }?.let { it2 -> muaSamListRequest.add(it2) }
                    }
                    val muaSamRequest = SuaChuaYeuCauRequest(edtTieuDeSuaChuaYeuCau.text.toString(), edtGiaiTrinhSuaChuaYeuCau.text.toString(), muaSamListRequest)

                    Handler().postDelayed({
                        yeuCauId?.let { it ->
                            viewModel.repairYeuCauMuaSam(it, muaSamRequest)
                                    .subscribeOn(Schedulers.io())
                                    ?.doFinally { dialog?.dismiss() }
                                    ?.observeOn(AndroidSchedulers.mainThread())
                                    ?.subscribe({
                                        (activity as MainActivity).apply {
                                            createYeuCauSuccess()
                                            popBackStackFragment()
                                        }
                                    }, {})
                        }
                    }, 1000)
                } else {
                    dialog?.show()
                    val suaChuaListRequest: MutableList<ItemSuaChuaRequest> = mutableListOf()
                    suaChuaList.forEach {
                        it?.itemProposalId?.let { it1 -> ItemSuaChuaRequest(it1, null) }?.let { it2 -> suaChuaListRequest.add(it2) }
                    }
                    val suaChuaRequest = SuaChuaYeuCauRequest(edtTieuDeSuaChuaYeuCau.text.toString(), edtGiaiTrinhSuaChuaYeuCau.text.toString(), suaChuaListRequest)

                    Handler().postDelayed({
                        yeuCauId?.let { it ->
                            viewModel.repairYeuCauMuaSam(it, suaChuaRequest)
                                    .subscribeOn(Schedulers.io())
                                    ?.doFinally { dialog?.dismiss() }
                                    ?.observeOn(AndroidSchedulers.mainThread())
                                    ?.subscribe({
                                        (activity as MainActivity).apply {
                                            createYeuCauSuccess()
                                            popBackStackFragment()
                                        }
                                    }, {})
                        }
                    }, 1000)
                }
            }
        }
    }

    private fun handleInterface() {
        suaChuaAdapter?.apply {
            isCheckEquipmentListener = {
                suaChuaList.add(thietBiList[it])
            }

            isNotCheckEquipmentListener = {
                suaChuaList.remove(thietBiList[it])
            }
        }

        muaSamAdapter?.apply {
            sentPositionXoaItemYeuCauSuaChuaMsListener = {
                muaSamList.remove(thietBiList[it])
                notifyDataSetChanged()
            }
        }
    }
}
