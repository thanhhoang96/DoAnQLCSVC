package com.example.thanhhoang.qlcosovatchat.ui.qlkh.taoKeHoach

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.EquipmentItem
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.Plans
import com.example.thanhhoang.qlcosovatchat.data.model.loaikehoach.ItemLoaiKeHoach
import com.example.thanhhoang.qlcosovatchat.data.model.nhomthietbi.NhomThietBi
import com.example.thanhhoang.qlcosovatchat.data.model.thietbi.ThietBi
import com.example.thanhhoang.qlcosovatchat.data.response.LoaiKeHoachResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.popBackStackFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_chon_thiet_bi_tmkh.view.*
import kotlinx.android.synthetic.main.fragment_tao_moi_kh.*

class TaoKeHoachFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: TaoKeHoachViewModel? = null
    private var taoKeHoachAdapter: TaoKeHoachAdapter? = null

    private var listTypePlan: MutableList<ItemLoaiKeHoach> = mutableListOf()
    private var listKeHoach: MutableList<ThietBi> = mutableListOf()
    private var listGroup: MutableList<NhomThietBi> = mutableListOf()
    private var listEquipment: MutableList<ThietBi> = mutableListOf()
    private var listNhomThietBi: MutableList<String> = mutableListOf()

    private var listLoaiKeHoach: MutableList<String> = mutableListOf()
    private var listThietBi: MutableList<String> = mutableListOf()

    private var positionLoaiPlan = 0
    private var positionGroup = 0
    private var positionEquipment = 0
    private var soLuong = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = TaoKeHoachViewModel(Repository())
        return inflater.inflate(R.layout.fragment_tao_moi_kh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        handleListener()
        handleInterfaceListener()
    }

    @SuppressLint("CheckResult")
    private fun initData() {
        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        viewModel?.getLoaiKeHoach()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    listTypePlan.apply {
                        clear()
                        addAll(it.data.loaiKeHoachList)
                    }
                    showLoaiKeHoachList(it)
                }, {})
        // init list data
        taoKeHoachAdapter = TaoKeHoachAdapter(listKeHoach)
        recyclerViewTaoMoiKh.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = taoKeHoachAdapter
        }

    }

    // event
    private fun handleListener() {
        spLoaiKeHoachTaoMoiKh.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionLoaiPlan = position
            }
        }

        btnChonThietBiTaoMoiKh.setOnClickListener { showDialogChonThietBi() }

        btnTaoGuiKeHoach.setOnClickListener { _ ->
            if (listKeHoach.size == 0 || edtTenKeHoachTaoMoi.text.toString().isEmpty())
                Toast.makeText(activity, "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show()
            else {
                dialog?.show()

                val listPlanItem: MutableList<EquipmentItem> = mutableListOf()
                listKeHoach.forEach {
                    it.soLuong?.let { it1 -> EquipmentItem(it.idThietBi, it1) }?.let { it2 -> listPlanItem.add(it2) }
                }
                val plan = Plans(edtTenKeHoachTaoMoi.text.toString(), edtGhiChuTaoMoiKh.text.toString(), listTypePlan[positionLoaiPlan].id, listPlanItem)

                Handler().postDelayed({
                    viewModel?.createNewKeHoach(plan)
                            ?.subscribeOn(Schedulers.io())
                            ?.doFinally { dialog?.dismiss() }
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe({
                                (activity as MainActivity).apply {
                                    createKeHoachSuccesListener()
                                    popBackStackFragment()
                                }
                            }, {})
                }, 2000)
            }
        }

        btnTroVeTaoMoiKh.setOnClickListener { (activity as MainActivity).popBackStackFragment() }
    }

    // interface
    private fun handleInterfaceListener() {
        (activity as MainActivity).dialogChonThietBiDismiss = {
            val thietBi = listEquipment[positionEquipment]
            thietBi.soLuong = soLuong
            listKeHoach.add(thietBi)
            taoKeHoachAdapter?.notifyDataSetChanged()
        }

        taoKeHoachAdapter?.sentPositionXoaItemTmkh = {
            listKeHoach.remove(listKeHoach[it])
            taoKeHoachAdapter?.notifyDataSetChanged()
        }
    }

    // get type plan
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun showLoaiKeHoachList(response: LoaiKeHoachResponse) {
        response.data.loaiKeHoachList.forEach {
            listLoaiKeHoach.add(it.name)
        }

        val adapter1 = ArrayAdapter<String>(activity as MainActivity, android.R.layout.simple_spinner_item, listLoaiKeHoach)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spLoaiKeHoachTaoMoiKh?.adapter = adapter1
    }

    // show dialog choose equipment
    @SuppressLint("InflateParams", "CheckResult")
    private fun showDialogChonThietBi() {
        listGroup.clear()
        soLuong = 0
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_chon_thiet_bi_tmkh, null)

        val mBuilder = context?.let {
            AlertDialog.Builder(it)
                    .setView(mDialogView)

        }
        val mAlertDialog = mBuilder?.show()

        // call api get group equipment
        viewModel?.getNhomThietBi()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ it ->
                    listGroup.apply {
                        clear()
                        addAll(it.data.listNhomThietBi)
                    }

                    listNhomThietBi.clear()
                    it.data.listNhomThietBi.forEach {
                        listNhomThietBi.add(it.name)
                    }
                    val adapterNhomThietBi = ArrayAdapter<String>(activity as MainActivity, android.R.layout.simple_spinner_item, listNhomThietBi)
                    adapterNhomThietBi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mDialogView.spNhomThietBi?.adapter = adapterNhomThietBi
                }, {})

        // select group equipment
        mDialogView.spNhomThietBi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionGroup = position
                listThietBi.clear()

                // call api get equipment
                viewModel?.getThietBi(listGroup[position].idGroupThietBi)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({ it ->
                            listEquipment.apply {
                                clear()
                                addAll(it.data.listThietBi)
                            }

                            it.data.listThietBi.forEach { listThietBi.add(it.name) }

                            val adapterThietBi = ArrayAdapter<String>(activity as MainActivity, android.R.layout.simple_spinner_item, listThietBi)
                            adapterThietBi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            mDialogView.spThietBi?.adapter = adapterThietBi

                        }, {})
            }
        }

        // select equipment
        mDialogView.spThietBi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionEquipment = position
            }
        }

        // handle Save data when click button Luu
        mDialogView.btnLuuChonThietBi.setOnClickListener {
            if (mDialogView.edtSoLuongChonThietBi.text.toString().isEmpty()) {
                Toast.makeText(activity as MainActivity, "Mời bạn nhập số lượng thiết bị trước khi Lưu", Toast.LENGTH_SHORT).show()
            } else {
                soLuong = mDialogView.edtSoLuongChonThietBi?.text.toString().toInt()
                (activity as MainActivity).dialogChonThietBiDismiss()
                mAlertDialog?.dismiss()
            }
        }

        // close dialog when click button huy
        mDialogView.btnHuyChonThietBi.setOnClickListener {
            mAlertDialog?.dismiss()
        }
    }
}
