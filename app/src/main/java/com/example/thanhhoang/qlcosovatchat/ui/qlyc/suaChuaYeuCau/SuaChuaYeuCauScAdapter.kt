package com.example.thanhhoang.qlcosovatchat.ui.qlyc.suaChuaYeuCau

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemYcDetail
import kotlinx.android.synthetic.main.item_sua_chua_yeu_cau_sua_chua.view.*

class SuaChuaYeuCauScAdapter(private var yeuCauSuaChuaList: MutableList<ItemYcDetail?>) : RecyclerView.Adapter<SuaChuaYeuCauScAdapter.SuaChuaYeuCauScVH>() {

    internal var isCheckEquipmentListener: (Int) -> Unit = {}
    internal var isNotCheckEquipmentListener: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SuaChuaYeuCauScVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sua_chua_yeu_cau_sua_chua, parent, false)
        return SuaChuaYeuCauScVH(view)
    }

    override fun getItemCount(): Int = yeuCauSuaChuaList.size

    override fun onBindViewHolder(viewHordel: SuaChuaYeuCauScVH, position: Int) {
        yeuCauSuaChuaList[position]?.let { viewHordel.onBind(it) }
    }

    inner class SuaChuaYeuCauScVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.cbChonSuaChuaYeuCauSc.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    isCheckEquipmentListener(adapterPosition)
                else
                    isNotCheckEquipmentListener(adapterPosition)
            }
        }

        fun onBind(itemDetail: ItemYcDetail) {
            itemView.run {
                cbChonSuaChuaYeuCauSc.isChecked = true
                tvThietBiSuaChuaYeuCauSc.text = itemDetail.equipment?.name
                tvMaDinhDanhSuaChuaYeuCauSc.text = itemDetail.maDinhDanh
                tvNhomThietBiSuaChuaYeuCauSc.text = itemDetail.groupEquipment?.name
                tvStatusSuaChuaYeuCauSc.text = "Hư hỏng"
            }
        }
    }
}
