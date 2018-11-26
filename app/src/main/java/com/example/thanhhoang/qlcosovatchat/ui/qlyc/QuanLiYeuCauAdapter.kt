package com.example.thanhhoang.qlcosovatchat.ui.qlyc

import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCau
import com.example.thanhhoang.qlcosovatchat.util.FormatUtils
import kotlinx.android.synthetic.main.item_qlyc.view.*

class QuanLiYeuCauAdapter(private var yeuCauList: MutableList<YeuCau>) : RecyclerView.Adapter<QuanLiYeuCauAdapter.QuanLiYeuCauVH>() {
    internal var sentPositionItemSuaYeuCau: (Int) -> Unit = {}
    internal var sentPositionItemXoaYeuCau: (Int) -> Unit = {}
    internal var sentPositionGetYeuCauDetail: (Int) -> Unit = {}

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
            itemView.apply {
                tvActionSuaYc.setOnClickListener {
                    sentPositionItemSuaYeuCau(adapterPosition)
                }

                tvActionXoaYc.setOnClickListener {
                    sentPositionItemXoaYeuCau(adapterPosition)
                }

                tvTieuDeYc.setOnClickListener {
                    sentPositionGetYeuCauDetail(adapterPosition)
                }
            }
        }

        fun onBind(yeuCau: YeuCau) {
            itemView.run {
                tvTieuDeYc.apply {
                    text = yeuCau.tieuDeYC
                    paintFlags = Paint.UNDERLINE_TEXT_FLAG
                }
                tvNgayTaoYc.text = FormatUtils.formatDisplayDate(yeuCau.ngayTaoYC)
                tvTrangThaiYc.text = if (yeuCau.trangThaiYC == 0) "Chưa duyệt" else
                    (if (yeuCau.trangThaiYC == 1) "Đã xác nhận" else "Đã duyệt")

                when {
                    tvTrangThaiYc.text == "Đã duyệt" -> {
                        tvTrangThaiYc.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                        llActionQlyc.visibility = View.GONE
                    }
                    tvTrangThaiYc.text == "Đã xác nhận" -> {
                        tvTrangThaiYc.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
                        llActionQlyc.visibility = View.VISIBLE
                    }
                    tvTrangThaiYc.text == "Chưa duyệt" -> {
                        tvTrangThaiYc.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                        llActionQlyc.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
