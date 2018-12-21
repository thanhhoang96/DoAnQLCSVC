package com.example.thanhhoang.qlcosovatchat.ui.qlyc.suaChuaYeuCau

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemYcDetail
import kotlinx.android.synthetic.main.item_sua_chua_yeu_cau_mua_sam.view.*

class SuaChuaYeuCauMsAdapter(private var yeuCauMsList: MutableList<ItemYcDetail?>) : RecyclerView.Adapter<SuaChuaYeuCauMsAdapter.SuaChuaYeuCauMsVH>() {

    internal var sentPositionXoaItemYeuCauSuaChuaMsListener: (Int) -> Unit = {}
    internal var tangSoLuongMsListener: (Int) -> Unit = {}
    internal var giamSoLuongMsListener: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SuaChuaYeuCauMsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sua_chua_yeu_cau_mua_sam, parent, false)
        return SuaChuaYeuCauMsVH(view)
    }

    override fun getItemCount(): Int = yeuCauMsList.size


    override fun onBindViewHolder(viewHordel: SuaChuaYeuCauMsVH, position: Int) {
        yeuCauMsList[position]?.let { viewHordel.onBind(it) }
    }

    inner class SuaChuaYeuCauMsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.apply {
                tvActionSuaChuaYeuCauMs.setOnClickListener {
                    sentPositionXoaItemYeuCauSuaChuaMsListener(adapterPosition)
                }

                imgUpSoLuong.setOnClickListener {
                    tangSoLuongMsListener(adapterPosition)
                }

                imgDownSoLuong.setOnClickListener {
                    giamSoLuongMsListener(adapterPosition)
                }
            }


        }

        fun onBind(itemDetail: ItemYcDetail) {
            itemView.run {
                tvThietBiSuaChuaYeuCauMs.text = itemDetail.planItem?.equipment?.name
                tvNhomThietBiSuaChuaYeuCauMs.text = itemDetail.planItem?.equipment?.equipmentGroup?.name
                tvDonViSuaChuaYeuCauMs.text = itemDetail.planItem?.equipment?.measureUnit?.name
                tvSlYeuCauSuaChuaYeuCauMs.text = itemDetail.soLuongYeuCau.toString()
                tvSlDuyetSuaChuaYeuCauMs.text = itemDetail.soLuongDuyet.toString()
            }
        }
    }
}
