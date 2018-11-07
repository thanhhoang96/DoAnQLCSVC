package com.example.thanhhoang.qlcosovatchat.ui.qlts

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Infra
import kotlinx.android.synthetic.main.item_qlts.view.*

class QuanLiTaiSanAdapter(private var infraList: MutableList<Infra>) : RecyclerView.Adapter<QuanLiTaiSanAdapter.QuanLiTaiSanVH>() {

    internal var sentPositionItemQlts: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): QuanLiTaiSanVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qlts, parent, false)
        return QuanLiTaiSanVH(view)
    }

    override fun getItemCount(): Int = infraList.size

    override fun onBindViewHolder(viewHordel: QuanLiTaiSanVH, position: Int) {
        viewHordel.onBind(infraList[position])
    }

    inner class QuanLiTaiSanVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tvActionQlts.setOnClickListener {
                sentPositionItemQlts(adapterPosition)
            }
        }

        fun onBind(infra: Infra) {
            itemView.run {
                tvMaDinhDanhQlts.text = infra.maDinhDanh
                tvThietBiQlts.text = infra.equipment.nameNo
                tvNhomThietBiQlts.text = infra.groupEquipment.name
                tvTrangThaiQlts.text = if (infra.unitEquipmentStatus == "HH") "Hu hong" else (if (infra.unitEquipmentStatus == "DSD") "Dang su dung" else "Dang sua chua")
                if (tvTrangThaiQlts.text == "Dang su dung") {
                    tvTrangThaiQlts.setTextColor(ContextCompat.getColor(context, R.color.colorGreenMedium))
                } else if (tvTrangThaiQlts.text == "Hu hong") {
                    tvTrangThaiQlts.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                } else {
                    tvActionQlts.visibility = View.GONE
                    tvTrangThaiQlts.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                }
            }
        }
    }
}
