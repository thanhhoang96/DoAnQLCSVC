package com.example.thanhhoang.qlcosovatchat.ui.qlyc.chiTietYeuCau

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemYcDetail
import kotlinx.android.synthetic.main.item_yc_detail.view.*

class YeuCauDetailAdapter(private var yeuCauDetailList: MutableList<ItemYcDetail?>) : RecyclerView.Adapter<YeuCauDetailAdapter.YeuCauDetailVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): YeuCauDetailVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_yc_detail, parent, false)
        return YeuCauDetailVH(view)
    }

    override fun getItemCount(): Int = yeuCauDetailList.size

    override fun onBindViewHolder(viewHordel: YeuCauDetailVH, position: Int) {
        yeuCauDetailList[position]?.let { viewHordel.onBind(it) }
    }

    inner class YeuCauDetailVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {}

        fun onBind(itemDetail: ItemYcDetail) {
            itemView.run {
                tvMaDinhDanhYcDetail.text = itemDetail.maDinhDanh
                tvTenThietYcDetail.text = itemDetail.equipment?.name
                tvNhomThietBiYcDetail.text = itemDetail.groupEquipment?.name
                tvDonViTinhYcDetail.text = itemDetail.equipment?.measureUnit?.name
            }
        }
    }
}