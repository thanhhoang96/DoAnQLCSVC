package com.example.thanhhoang.qlcosovatchat.ui.qlts

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.TaiSan
import kotlinx.android.synthetic.main.item_qlts.view.*

class QuanLiTaiSanAdapter(private var taiSanList: MutableList<TaiSan>) : RecyclerView.Adapter<QuanLiTaiSanAdapter.QuanLiTaiSanVH>() {

    internal var sentPositionItemQlts: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): QuanLiTaiSanVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qlts, parent, false)
        return QuanLiTaiSanVH(view)
    }

    override fun getItemCount(): Int = taiSanList.size

    override fun onBindViewHolder(viewHordel: QuanLiTaiSanVH, position: Int) {
        viewHordel.onBind(taiSanList[position])
    }

    inner class QuanLiTaiSanVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tvActionQlts.setOnClickListener {
                sentPositionItemQlts(adapterPosition)
            }
        }

        fun onBind(taiSan: TaiSan) {
            itemView.run {
                tvMaDinhDanhQlts.text = taiSan.maDinhDanh
                tvThietBiQlts.text = taiSan.tenThietBi
                tvNhomThietBiQlts.text = taiSan.nhomThietBi
                tvTrangThaiQlts.text = taiSan.trangThai
                if (tvTrangThaiQlts.text == "Dang su dung") {
                    tvTrangThaiQlts.setTextColor(ContextCompat.getColor(context, R.color.colorGreenMedium))
                } else {
                    tvTrangThaiQlts.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                }
            }
        }
    }
}
