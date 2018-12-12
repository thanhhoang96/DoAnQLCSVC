package com.example.thanhhoang.qlcosovatchat.ui.qlyc.chiTietYeuCau

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemYcDetail
import kotlinx.android.synthetic.main.item_yc_ms_detail.view.*

class YeuCauMsDetailAdapter(private var yeuCauMsDetailList: MutableList<ItemYcDetail?>) : RecyclerView.Adapter<YeuCauMsDetailAdapter.YeuCauMsDetailVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): YeuCauMsDetailVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_yc_ms_detail, parent, false)
        return YeuCauMsDetailVH(view)
    }

    override fun getItemCount(): Int = yeuCauMsDetailList.size

    override fun onBindViewHolder(viewHordel: YeuCauMsDetailVH, position: Int) {
        yeuCauMsDetailList[position]?.let { viewHordel.onBind(it) }
    }

    inner class YeuCauMsDetailVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }

        fun onBind(itemDetail: ItemYcDetail) {
            itemView.run {
                tvTenThietBiYcMsDetail.text = itemDetail.planItem?.equipment?.name
                tvTenNhomThietBiYcMsDetail.text = itemDetail.planItem?.equipment?.equipmentGroup?.name
                tvDonViTinhYcMsDetail.text = itemDetail.planItem?.equipment?.measureUnit?.name
                tvSoLuongDeNghiYcMsDetail.text = itemDetail.soLuongYeuCau.toString()
                tvSoLuongDuyetYcMsDetail.text = itemDetail.soLuongDuyet.toString()
            }
        }
    }
}