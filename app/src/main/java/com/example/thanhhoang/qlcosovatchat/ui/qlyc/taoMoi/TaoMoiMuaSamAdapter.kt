package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.ItemKhDetail
import kotlinx.android.synthetic.main.item_qlyc_tmms.view.*

class TaoMoiMuaSamAdapter(private var yeuCauMuaSamList: MutableList<ItemKhDetail>) : RecyclerView.Adapter<TaoMoiMuaSamAdapter.TaoMoiMuaSamVH>() {
    internal var sentPositionXoaItemYcms: (Int) -> Unit = {}
    internal var tangSoLuongYcms: (Int) -> Unit = {}
    internal var giamSoLuongYcms: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TaoMoiMuaSamVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qlyc_tmms, parent, false)
        return TaoMoiMuaSamVH(view)
    }

    override fun getItemCount(): Int = yeuCauMuaSamList.size

    override fun onBindViewHolder(viewHordel: TaoMoiMuaSamVH, position: Int) {
        viewHordel.onBind(yeuCauMuaSamList[position])
    }

    inner class TaoMoiMuaSamVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.apply {
                tvDeleteTmms.setOnClickListener {
                    sentPositionXoaItemYcms(adapterPosition)
                }

                imgUpSoLuongTaoMoiMs.setOnClickListener {
                    tangSoLuongYcms(adapterPosition)
                }

                imgDownSoLuongTaoMoiMs.setOnClickListener {
                    giamSoLuongYcms(adapterPosition)
                }
            }
        }

        fun onBind(plan: ItemKhDetail) {
            itemView.run {
                tvTenThietBiTmms.text = plan.equipment.name
                tvNhomThietBiTmms.text = plan.equipment.equipmentGroup.name
                tvSoLuongYcTmms.text = plan.soLuongConLai.toString()
            }
        }
    }
}
