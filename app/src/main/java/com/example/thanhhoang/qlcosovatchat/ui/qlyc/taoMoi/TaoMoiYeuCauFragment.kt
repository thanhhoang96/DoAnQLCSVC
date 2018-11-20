package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

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
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Infra
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCau
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
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

    private var isTaoMoiSuaChua = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tao_moi_yc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initView()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initView() {
        suaChuaList = arrayListOf()
        muaSamList = arrayListOf()
        viewModel = TaoMoiYeuCauViewModel(Repository())

        if (!isTaoMoiSuaChua) {
            // todo handle bind data for recyclerview mua sam
        }
    }

    private fun handleListener() {
        rgYeuCau.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == rbMuaSam.id) {
                isTaoMoiSuaChua = false
            } else {
                isTaoMoiSuaChua = true
                loadDataSuaChua()

            }
        }
    }

    private fun handleListenerFromInterface() {}

    private fun loadDataSuaChua() {
        dialog?.show()
        Handler().postDelayed({
            viewModel?.getAllTaiSanHuHong()
                    ?.subscribeOn(Schedulers.io())
                    ?.doFinally { dialog?.dismiss() }
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        suaChuaList = it.data.taiSanList
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
    }
}
