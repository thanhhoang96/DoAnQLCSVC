package com.example.thanhhoang.qlcosovatchat.ui.qlyc

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
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCau
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.addFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlts.QuanLiTaiSanViewModel
import com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi.TaoMoiYeuCauFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_quan_li_yeu_cau.*

class QuanLiYeuCauFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: QuanLiYeuCauViewModel? = null
    private var yeuCauList: MutableList<YeuCau>? = null
    private var yeuCauAdapter: QuanLiYeuCauAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quan_li_yeu_cau, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initData()
        initView()
        connApi()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initData() {}

    private fun initView() {
        yeuCauList = arrayListOf()
        yeuCauAdapter = yeuCauList?.let { QuanLiYeuCauAdapter(it) }
        recyclerViewListQlyc.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = yeuCauAdapter
        }
    }

    private fun connApi(){
        dialog?.show()
        viewModel = QuanLiYeuCauViewModel(Repository())
        Handler().postDelayed({
            viewModel?.getAllYeuCau()
                    ?.subscribeOn(Schedulers.io())
                    ?.doFinally { dialog?.dismiss() }
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        updateList(it)
                    }, {})
        }, 2000)
    }

    private fun handleListener() {
        btnTaoMoiYc.setOnClickListener {
            activity?.addFragment(R.id.flContainer, TaoMoiYeuCauFragment())
        }

        imgClearInputQlyc.setOnClickListener {
            edtSearchQlyc.setText("")
        }
    }

    private fun handleListenerFromInterface(){

    }

    private fun updateList(yeuCauResponse: YeuCauResponse) {
        yeuCauList?.apply {
            clear()
            addAll(yeuCauResponse.data.yeuCauList)
        }
        yeuCauAdapter?.notifyDataSetChanged()
    }
}
