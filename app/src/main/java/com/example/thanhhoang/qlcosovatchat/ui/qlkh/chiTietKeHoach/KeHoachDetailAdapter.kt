package com.example.thanhhoang.qlcosovatchat.ui.qlkh.chiTietKeHoach

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.ItemKhDetail
import com.example.thanhhoang.qlcosovatchat.util.FormatUtils
import kotlinx.android.synthetic.main.item_kh_detail.view.*

class KeHoachDetailAdapter(private var keHoachDetailList: MutableList<ItemKhDetail>) : RecyclerView.Adapter<KeHoachDetailAdapter.KeHoachDetailVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): KeHoachDetailVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kh_detail, parent, false)
        return KeHoachDetailVH(view)
    }

    override fun getItemCount(): Int = keHoachDetailList.size

    override fun onBindViewHolder(viewHordel: KeHoachDetailVH, position: Int) {
        viewHordel.onBind(keHoachDetailList[position])
    }

    inner class KeHoachDetailVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }

        fun onBind(itemDetail: ItemKhDetail) {
            itemView.run {
                tvTenThietBiKhDetail.text = itemDetail.equipment.name
                tvTenNhomThietBiKhDetail.text = itemDetail.equipment.equipmentGroup.name
                tvDonViTinhKhDetail.text = itemDetail.equipment.measureUnit.name
                tvSoLuongDeNghiKhDetail.text = itemDetail.soLuongDeNghi.toString()
                tvSoLuongDuyetKhDetail.text = itemDetail.soLuongPheDuyet.toString()
                tvDonGiaKhDetail.text = FormatUtils.formatNumber(itemDetail.equipment.donGia?.toLong() as Long)
            }
        }
    }
}