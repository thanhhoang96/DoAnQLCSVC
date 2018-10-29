package com.example.thanhhoang.qlcosovatchat.ui.qlyc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.YeuCau
import com.example.thanhhoang.qlcosovatchat.extention.addFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi.TaoMoiYeuCauFragment
import kotlinx.android.synthetic.main.fragment_quan_li_yeu_cau.*

class QuanLiYeuCauFragment : Fragment() {
    private var yeuCauList: MutableList<YeuCau>? = null
    private var yeuCauAdapter: QuanLiYeuCauAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quan_li_yeu_cau, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData()
        initView()
        handleListener()
    }

    private fun initData() {
        yeuCauList = arrayListOf()
        yeuCauList?.add(YeuCau("Mua moi may tinh", "10-12-3018", "Da duyet"))
        yeuCauList?.add(YeuCau("Mua moi may in", "12-18-3018", "Chua duyet"))
    }

    private fun initView() {
        yeuCauAdapter = yeuCauList?.let { QuanLiYeuCauAdapter(it) }
        recyclerViewListQlyc.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = yeuCauAdapter
        }
    }

    private fun handleListener() {
        btnTaoMoiYc.setOnClickListener {
            activity?.addFragment(R.id.flContainer, TaoMoiYeuCauFragment())
        }

        imgClearInputQlyc.setOnClickListener {
            edtSearchQlyc.setText("")
        }
    }
}
