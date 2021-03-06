package com.example.thanhhoang.qlcosovatchat

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.example.thanhhoang.qlcosovatchat.data.source.repository.LocalRepository
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.*
import com.example.thanhhoang.qlcosovatchat.ui.login.LoginActivity
import com.example.thanhhoang.qlcosovatchat.ui.qlkh.QuanLiKeHoachFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlts.QuanLiTaiSanFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlyc.QuanLiYeuCauFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var actionBar: ActionBar? = null
    private val repository = Repository()
    private val localRepository = LocalRepository()

    internal var createYeuCauSuccess: () -> Unit = {}
    internal var sentYeuCauFromDialog: () -> Unit = {}

    internal var dialogChonThietBiDismiss: () -> Unit = {}
    internal var createKeHoachSuccesListener: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        updateView()
        handleListener()
    }

    override fun onBackPressed() {
        val countFragmentBackStack = supportFragmentManager.backStackEntryCount
        if (countFragmentBackStack <= 1) {
            if (supportFragmentManager.findFragmentById(R.id.flContainer) is QuanLiTaiSanFragment)
                moveTaskToBack(true)
            else
                replaceFragment(R.id.flContainer, QuanLiTaiSanFragment())
        } else
            popBackStackFragment()
    }

    private fun initView() {
        setSupportActionBar(toolbarMain)
        actionBar = supportActionBar
        setTitleMenu("Quản lí tài sản")

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayoutContain,
                toolbarMain,
                R.string.menu_drawer_open,
                R.string.menu_drawer_close
        ) {}

        supportActionBar?.setDisplayShowHomeEnabled(true)

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayoutContain.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    fun setTitleMenu(screenName: String){
        setTitleToolbar(screenName)
    }

    private fun updateView() {
        tvPerson.text = repository.getFullName()
        addFragment(R.id.flContainer, QuanLiTaiSanFragment())
    }

    private fun handleListener() {
        tvQuanLiTaiSan.setOnClickListener {
            drawerLayoutContain.closeDrawer(GravityCompat.START)
            replaceFragment(R.id.flContainer, QuanLiTaiSanFragment())

        }
        tvQuanLiYeuCau.setOnClickListener {
            drawerLayoutContain.closeDrawer(GravityCompat.START)
            replaceFragment(R.id.flContainer, QuanLiYeuCauFragment())

        }
        tvQuanLikeHoach.setOnClickListener {
            drawerLayoutContain.closeDrawer(GravityCompat.START)
            replaceFragment(R.id.flContainer, QuanLiKeHoachFragment())
        }
        tvLogout.setOnClickListener {
            showDialog(
                    "Bạn có chắc chắn muốn đăng xuất không?",
                    "Ok",
                    "Huỷ",
                    DialogInterface.OnClickListener { _, _ ->
                        localRepository.saveAccessToken("")
                        moveActivity(Intent(this, LoginActivity::class.java))
                    })
            drawerLayoutContain.closeDrawer(GravityCompat.START)
        }
    }

    private fun setTitleToolbar(title: String) {
        actionBar?.title = title
    }
}
