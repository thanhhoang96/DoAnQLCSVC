package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Infra
import kotlinx.android.synthetic.main.item_qlyc_tmsc.view.*

class TaoMoiSuaChuaAdapter(private var suaChuaList: MutableList<Infra>) : RecyclerView.Adapter<TaoMoiSuaChuaAdapter.TaoMoiSuaChuaVH>() {
    internal var sentPositionTaiSanHuHongIsCheck: (Int) -> Unit = {}
    internal var sentPositionTaiSanHuHongNotCheck: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TaoMoiSuaChuaVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qlyc_tmsc, parent, false)
        return TaoMoiSuaChuaVH(view)
    }

    override fun getItemCount(): Int = suaChuaList.size

    override fun onBindViewHolder(viewHordel: TaoMoiSuaChuaVH, position: Int) {
        viewHordel.onBind(suaChuaList[position])
    }

    inner class TaoMoiSuaChuaVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.cbSelectSuaChua.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    sentPositionTaiSanHuHongIsCheck(adapterPosition)
                else
                    sentPositionTaiSanHuHongNotCheck(adapterPosition)
            }
        }

        fun onBind(taiSan: Infra) {
            itemView.run {
                tvTenThietBiSc.text = taiSan.equipment.name
                tvMaDinhDanhSc.text = taiSan.maDinhDanh
                tvNhomThietBiSc.text = taiSan.groupEquipment.name
                tvTrangThaiThietBiSc.text = if (taiSan.unitEquipmentStatus == "HH") "Hư hỏng" else ""
            }
        }
    }
}
