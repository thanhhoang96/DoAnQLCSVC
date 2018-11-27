package com.example.thanhhoang.qlcosovatchat.ui.qlkh

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.KeHoach
import com.example.thanhhoang.qlcosovatchat.data.response.KeHoachResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.addFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlkh.chiTietKeHoach.KeHoachDetailFragment
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_quan_li_ke_hoach.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class QuanLiKeHoachFragment : Fragment() {
    private var dialog: Dialog? = null
    private var viewModel: QuanLiKeHoachViewModel? = null
    private var keHoachList: MutableList<KeHoach>? = null
    private var keHoachAdapter: QuanLiKeHoachAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        return inflater.inflate(R.layout.fragment_quan_li_ke_hoach, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = DialogProgressbarUtils.showProgressDialog(activity as MainActivity)
        dialog?.setCancelable(false)

        initView()
        connApi()
        handleListener()
        handleListenerFromInterface()
    }

    private fun initView() {
        keHoachList = arrayListOf()
        keHoachAdapter = keHoachList?.let { QuanLiKeHoachAdapter(it) }
        recyclerViewQlkh.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = keHoachAdapter
        }
    }

    private fun connApi() {
        dialog?.show()
        viewModel = QuanLiKeHoachViewModel(Repository())
        Handler().postDelayed({
            viewModel?.getAllKeHoach()
                    ?.subscribeOn(Schedulers.io())
                    ?.doFinally { dialog?.dismiss() }
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        updateList(it)
                    }, {})
        }, 2000)
    }

    private fun handleListener() {
        imgClearInputQlkh.setOnClickListener {
            edtSearchQlkh.setText("")
        }
    }

    private fun handleListenerFromInterface() {
        keHoachAdapter?.apply {
            sentPositionItemQlKh = {
                Log.d("xxx", it.toString())
            }

            sentPositionItemKhDtail = {
                val bundle = Bundle()
                bundle.putString("keHoachID", keHoachList?.get(it)?.id)
                val keHoachDetail = KeHoachDetailFragment()
                keHoachDetail.arguments = bundle
                (activity as MainActivity).addFragment(R.id.flContainer, keHoachDetail)
            }
        }
    }

    private fun updateList(responseData: KeHoachResponse) {
        keHoachList?.apply {
            clear()
            addAll(responseData.data.keHoachList)
        }
        keHoachAdapter?.notifyDataSetChanged()
        recyclerViewQlkh.scrollToPosition(0)
    }
}