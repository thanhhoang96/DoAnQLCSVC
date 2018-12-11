package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.ItemKhDetail
import kotlinx.android.synthetic.main.item_plan_detail_for_ycms.view.*

class DialogPlanAdapter(private var planList: MutableList<ItemKhDetail>) : RecyclerView.Adapter<DialogPlanAdapter.DialogPlanVH>() {
    internal var sentPositionPlanDetailIsCheck: (Int) -> Unit = {}
    internal var sentPositionPlanDetailNotCheck: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DialogPlanVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plan_detail_for_ycms, parent, false)
        return DialogPlanVH(view)
    }

    override fun getItemCount(): Int = planList.size

    override fun onBindViewHolder(viewHordel: DialogPlanVH, position: Int) {
        viewHordel.onBind(planList[position])
    }

    inner class DialogPlanVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.cbTheoPlanYcms.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    sentPositionPlanDetailIsCheck(adapterPosition)
                else
                    sentPositionPlanDetailNotCheck(adapterPosition)
            }
        }

        fun onBind(plan: ItemKhDetail) {
            itemView.run {
                tvTenThietBiTheoPlanYcms.text = plan.equipment.name
                tvTenNhomThietBiTheoPlanYcms.text = plan.equipment.equipmentGroup.name
                tvSoLuongTheoPlanYcms.text = plan.soLuongDeNghi.toString()
                tvSoLuongConLaiTheoPlanYcms.text = plan.soLuongConLai.toString()
                tvSoLuongDangYeuCauTheoPlanYcms.text = plan.soLuongDangYeuCau.toString()
            }
        }
    }
}
