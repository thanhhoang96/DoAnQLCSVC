package com.example.thanhhoang.qlcosovatchat.ui.qlkh.taoKeHoach

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.thietbi.ThietBi
import kotlinx.android.synthetic.main.item_tao_moi_ke_hoach.view.*

class TaoKeHoachAdapter(private var listThietBi: MutableList<ThietBi>) : RecyclerView.Adapter<TaoKeHoachAdapter.TaoKeHoachVH>() {

    internal var sentPositionXoaItemTmkh: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TaoKeHoachVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tao_moi_ke_hoach, parent, false)
        return TaoKeHoachVH(view)
    }

    override fun getItemCount(): Int = listThietBi.size

    override fun onBindViewHolder(viewHordel: TaoKeHoachVH, position: Int) {
        viewHordel.onBind(listThietBi[position])
    }

    inner class TaoKeHoachVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tvXoaItemTmkh.setOnClickListener {
                sentPositionXoaItemTmkh(adapterPosition)
            }
        }

        fun onBind(thietBi: ThietBi) {
            itemView.run {
                tvTenThietBiItemTmkh.text = thietBi.name
                tvNhomThietBiItemTmkh.text = thietBi.equipmentGroup.name
                tvDonViItemTmkh.text = thietBi.donVi.name
                tvSlDeNghiItemTmkh.text = thietBi.soLuong.toString()
                tvDonGiaItemTmkh.text = thietBi.unitPrice.toString()
                tvThanhTienItemTmkh.text = (thietBi.soLuong?.times(thietBi.unitPrice)).toString()
            }
        }
    }
}

