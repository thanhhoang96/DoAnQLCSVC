package com.example.thanhhoang.qlcosovatchat.ui.qlkh

import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.KeHoach
import com.example.thanhhoang.qlcosovatchat.util.FormatUtils
import kotlinx.android.synthetic.main.item_qlkh.view.*

class QuanLiKeHoachAdapter(private var keHoachList: MutableList<KeHoach>) : RecyclerView.Adapter<QuanLiKeHoachAdapter.QuanLiKeHoachVH>() {

    internal var sentPositionXoaItemQlKh: (Int) -> Unit = {}
    internal var sentPositionSuaItemQlKh: (Int) -> Unit = {}
    internal var sentPositionItemKhDtail: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): QuanLiKeHoachVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qlkh, parent, false)
        return QuanLiKeHoachVH(view)
    }

    override fun getItemCount(): Int = keHoachList.size

    override fun onBindViewHolder(viewHordel: QuanLiKeHoachVH, position: Int) {
        viewHordel.onBind(keHoachList[position])
    }

    inner class QuanLiKeHoachVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tvXoaQlkh.setOnClickListener {
                sentPositionXoaItemQlKh(adapterPosition)
            }

            itemView.tvSuaQlkh.setOnClickListener {
                sentPositionSuaItemQlKh(adapterPosition)
            }

            itemView.tvTenKhQlkh.setOnClickListener {
                sentPositionItemKhDtail(adapterPosition)
            }
        }

        fun onBind(keHoach: KeHoach) {
            itemView.run {
                tvTenKhQlkh.apply {
                    text = keHoach.tieuDeKeHoach
                    setTextColor(ContextCompat.getColor(context, R.color.colorBlueMedium))
                    paintFlags = Paint.UNDERLINE_TEXT_FLAG
                }

                tvLoaiKhQlkh.text = keHoach.loaiKeHoach?.name
                tvNgayTaoQlkh.text = FormatUtils.formatDisplayDate(keHoach.ngayTaoKeHoach)
                tvTrangThaiQlKh.text = if (keHoach.trangThaiKeHoach == 0) "Chưa duyệt" else (if (keHoach.trangThaiKeHoach == 1) "Đã xác nhận" else "Đã duyệt")

                when {
                    tvTrangThaiQlKh.text == "Đã duyệt" -> {
                        tvTrangThaiQlKh.setTextColor(ContextCompat.getColor(context, R.color.colorGreenMedium))
                        tvXoaQlkh.visibility = View.GONE
                    }
                    tvTrangThaiQlKh.text == "Đã xác nhận" -> {
                        tvTrangThaiQlKh.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
                        tvXoaQlkh.visibility = View.VISIBLE
                    }
                    tvTrangThaiQlKh.text == "Chưa duyệt" -> {
                        tvTrangThaiQlKh.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                        tvXoaQlkh.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
