package com.example.thanhhoang.qlcosovatchat.ui.qlkh

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.KeHoach
import kotlinx.android.synthetic.main.item_qlkh.view.*

class QuanLiKeHoachAdapter(private var keHoachList: MutableList<KeHoach>) : RecyclerView.Adapter<QuanLiKeHoachAdapter.QuanLiKeHoachVH>() {

    internal var sentPositionItemQlKh: (Int) -> Unit = {}

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
            itemView.tvActionQlkh.setOnClickListener {
                sentPositionItemQlKh(adapterPosition)
            }
        }

        fun onBind(keHoach: KeHoach) {
            itemView.run {
                tvMaQlkh.text = keHoach.maKeHoach
                tvTenKhQlkh.text = keHoach.tenKeHoach
                tvLoaiKhQlkh.text = keHoach.loaiKeHoach
                tvNgayTaoQlkh.text = keHoach.ngayTaoKh
                tvTrangThaiQlKh.text = keHoach.trangThai
                if (tvTrangThaiQlKh.text == "Da xac nhan") {
                    tvTrangThaiQlKh.setTextColor(ContextCompat.getColor(context, R.color.colorGreenMedium))
                    tvActionQlkh.visibility = View.GONE
                } else {
                    tvTrangThaiQlKh.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                    tvActionQlkh.visibility = View.VISIBLE
                }
            }
        }
    }
}
