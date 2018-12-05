package com.example.thanhhoang.qlcosovatchat.ui.qlkh.taoKeHoach

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.nhomthietbi.NhomThietBi
import com.example.thanhhoang.qlcosovatchat.data.response.LoaiKeHoachResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_chon_thiet_bi_tmkh.*
import kotlinx.android.synthetic.main.dialog_chon_thiet_bi_tmkh.view.*
import kotlinx.android.synthetic.main.fragment_tao_moi_kh.*

class TaoKeHoachFragment : Fragment() {
    private var viewModel: TaoKeHoachViewModel? = null
    private var listKeHoach: MutableList<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = TaoKeHoachViewModel(Repository())
        return inflater.inflate(R.layout.fragment_tao_moi_kh, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        handleListener()
    }

    @SuppressLint("CheckResult")
    private fun initData() {
        listKeHoach = arrayListOf()
        viewModel?.getLoaiKeHoach()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    showLoaiKeHoachList(it)
                }, {})
    }

    private fun handleListener() {
        spLoaiKeHoachTaoMoiKh?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }

        btnChonThietBiTaoMoiKh.setOnClickListener {
            showDialogChonThietBi()
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun showLoaiKeHoachList(response: LoaiKeHoachResponse) {
        response.data.loaiKeHoachList.forEach {
            listKeHoach?.add(it.name)
        }

        val adapter1 = ArrayAdapter<String>(activity as MainActivity, android.R.layout.simple_spinner_item, listKeHoach)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spLoaiKeHoachTaoMoiKh?.adapter = adapter1
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("InflateParams", "CheckResult")
    private fun showDialogChonThietBi() {
        val listGroup = arrayListOf<NhomThietBi>()
        val listNhomThietBi = arrayListOf<String>()
        val listThietBi = arrayListOf<String>()
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_chon_thiet_bi_tmkh, null)

        val mBuilder = context?.let {
            AlertDialog.Builder(it)
                    .setView(mDialogView)

        }
        val mAlertDialog = mBuilder?.show()

        viewModel?.getNhomThietBi()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ it ->
                    listGroup.addAll(it.data.listNhomThietBi)

                    it.data.listNhomThietBi.forEach {
                        listNhomThietBi.add(it.name)
                    }
                    val adapterNhomThietBi = ArrayAdapter<String>(activity as MainActivity, android.R.layout.simple_spinner_item, listNhomThietBi)
                    adapterNhomThietBi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spNhomThietBi?.adapter = adapterNhomThietBi
                }, {})

        mDialogView.spNhomThietBi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel?.getThietBi(listGroup[position].idGroupThietBi)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({ it ->
                            it.data.listThietBi.forEach {
                                listThietBi.add(it.name)

                                val adapterThietBi = ArrayAdapter<String>(activity as MainActivity, android.R.layout.simple_spinner_item, listThietBi)
                                adapterThietBi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spNhomThietBi?.adapter = adapterThietBi
                            }
                        }, {})
            }

        }

        // handle Save data when click button Luu
        mDialogView.btnLuuChonThietBi.setOnClickListener {
            mAlertDialog?.dismiss()
        }

        // close dialog when click button huy
        mDialogView.btnHuyChonThietBi.setOnClickListener {
            mAlertDialog?.dismiss()
        }

        // handle dialog dismiss
        mAlertDialog?.setOnDismissListener { _ ->

        }
    }
}
