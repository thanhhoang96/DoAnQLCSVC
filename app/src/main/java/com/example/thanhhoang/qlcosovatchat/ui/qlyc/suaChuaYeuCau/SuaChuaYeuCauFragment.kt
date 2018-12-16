package com.example.thanhhoang.qlcosovatchat.ui.qlyc.suaChuaYeuCau

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemYcDetail
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
    }

    private fun handleListener() {
        btnTroVeSuaChuaYeuCau.setOnClickListener {
            (activity as MainActivity).popBackStackFragment()
        }

        btnSuaChuaYeuCau.setOnClickListener {
            Toast.makeText(activity, "sua chua", Toast.LENGTH_SHORT).show()
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

            notifyDataSetChanged()
        }

        muaSamAdapter?.apply {
            sentPositionXoaItemYeuCauSuaChuaMsListener = {
                muaSamList.remove(thietBiList[it])
            }

            notifyDataSetChanged()
        }
    }
}
