package com.example.thanhhoang.qlcosovatchat.ui.qlkh

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.KeHoach
import kotlinx.android.synthetic.main.fragment_quan_li_ke_hoach.*

class QuanLiKeHoachFragment : Fragment() {

    private var keHoachList: MutableList<KeHoach>? = null
    private var keHoachAdapter: QuanLiKeHoachAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quan_li_ke_hoach, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initView()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initData() {
        keHoachList = arrayListOf()
        keHoachList?.add(KeHoach("KH001", "Mua moi may tinh", "Theo nam", "12-12-2018", "Da xac nhan"))
        keHoachList?.add(KeHoach("KH002", "Mua moi may in", "Theo nam", "12-12-2018", "Chua xac nhan"))
        keHoachList?.add(KeHoach("KH003", "Mua moi may chieu", "Theo nam", "12-12-2018", "Da huy"))
    }

    private fun initView() {
        keHoachAdapter = keHoachList?.let { QuanLiKeHoachAdapter(it) }
        recyclerViewQlkh.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = keHoachAdapter
        }
    }

    private fun handleListener() {
        imgClearInputQlkh.setOnClickListener {
            edtSearchQlkh.setText("")
        }
    }

    private fun handleListenerFromInterface() {
        keHoachAdapter?.sentPositionItemQlKh = {
            Log.d("xxx", it.toString())
        }
    }
}