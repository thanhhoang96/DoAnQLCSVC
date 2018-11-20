package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCau
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import kotlinx.android.synthetic.main.fragment_tao_moi_yc.*

class TaoMoiYeuCauFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: TaoMoiYeuCauViewModel? = null
    private var suaChuaList: MutableList<YeuCau>? = null
    private var muaSamList: MutableList<YeuCau>? = null
    private var suaChuaAdapter: TaoMoiSuaChuaAdapter? = null
    private var muaSamAdapter: TaoMoiMuaSamAdapter? = null

    private var isTaoMoiSuaChua = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tao_moi_yc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initView()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initView() {
        suaChuaList = arrayListOf()
        muaSamList = arrayListOf()

        if (!isTaoMoiSuaChua) {
            // todo handle bind data for recyclerview mua sam
        }
    }

    private fun handleListener() {
        rgYeuCau.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == rbMuaSam.id) {
                isTaoMoiSuaChua = false
                Toast.makeText(activity, isTaoMoiSuaChua.toString(), Toast.LENGTH_SHORT).show()
            } else {
                isTaoMoiSuaChua = true
                Toast.makeText(activity, isTaoMoiSuaChua.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleListenerFromInterface() {

    }

    private fun loadData() {

    }
}
