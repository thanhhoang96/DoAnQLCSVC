package com.example.thanhhoang.qlcosovatchat.ui.qlyc

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCau
import kotlinx.android.synthetic.main.item_qlyc.view.*

class QuanLiYeuCauAdapter(private var yeuCauList: MutableList<YeuCau>) : RecyclerView.Adapter<QuanLiYeuCauAdapter.QuanLiYeuCauVH>() {
    internal var sentPositionItemQlyc: (Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): QuanLiYeuCauVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qlyc, parent, false)
        return QuanLiYeuCauVH(view)
    }

    override fun getItemCount(): Int = yeuCauList.size

    override fun onBindViewHolder(viewHordel: QuanLiYeuCauVH, position: Int) {
        viewHordel.onBind(yeuCauList[position])
    }

    inner class QuanLiYeuCauVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tvActionSuaYc.setOnClickListener {
                sentPositionItemQlyc(adapterPosition)
            }
            itemView.tvActionXoaYc.setOnClickListener {
                sentPositionItemQlyc(adapterPosition)
            }
            itemView.apply {
                setOnClickListener {
                }
            }
        }

        fun onBind(yeuCau: YeuCau) {
            itemView.run {
                tvTieuDeYc.text = yeuCau.tieuDeYC
                tvNgayTaoYc.text = yeuCau.ngayTaoYC
                tvTrangThaiYc.text = if(yeuCau.trangThaiYC == 0) "Chua duyet" else ( if(yeuCau.trangThaiYC == 1) "Da xac nhan" else "Da duyet")
                if (tvTrangThaiYc.text == "Da duyet") {
                    tvTrangThaiYc.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                    llActionQlyc.visibility = View.GONE
                } else {
                    tvTrangThaiYc.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                    llActionQlyc.visibility = View.VISIBLE
                }
            }
        }
    }
}
