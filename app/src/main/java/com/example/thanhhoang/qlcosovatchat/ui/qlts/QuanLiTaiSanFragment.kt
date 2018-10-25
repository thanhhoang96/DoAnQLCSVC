package com.example.thanhhoang.qlcosovatchat.ui.qlts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.TaiSan
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

    private fun handleListenerFromInterface() {
        taiSanAdapter?.sentPositionItemQlts = {
            Log.d("xxx", it.toString())
        }
    }
}
