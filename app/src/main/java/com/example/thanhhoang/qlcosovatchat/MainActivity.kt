package com.example.thanhhoang.qlcosovatchat

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.addFragment
import com.example.thanhhoang.qlcosovatchat.extention.replaceFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlkh.QuanLiKeHoachFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlts.QuanLiTaiSanFragment
import com.example.thanhhoang.qlcosovatchat.ui.qlyc.QuanLiYeuCauFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var actionBar: ActionBar? = null
    private val repository = Repository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        updateView()
        handleListener()
    }

    private fun initView() {
        setSupportActionBar(toolbarMain)
        actionBar = supportActionBar
        setTitleToolbar("Quản lí tài sản")

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

    private fun updateView() {
        tvPerson.text = repository.getFullName()
        addFragment(R.id.flContainer, QuanLiTaiSanFragment())
    }

    private fun handleListener() {
        tvQuanLiTaiSan.setOnClickListener {
            setTitleToolbar("Quản lí tài sản")
            drawerLayoutContain.closeDrawer(GravityCompat.START)
            replaceFragment(R.id.flContainer, QuanLiTaiSanFragment())

        }
        tvQuanLiYeuCau.setOnClickListener {
            setTitleToolbar("Quản lí yêu cầu")
            drawerLayoutContain.closeDrawer(GravityCompat.START)
            replaceFragment(R.id.flContainer, QuanLiYeuCauFragment())

        }
        tvQuanLikeHoach.setOnClickListener {
            setTitleToolbar("Quản lí kế hoạch")
            drawerLayoutContain.closeDrawer(GravityCompat.START)
            replaceFragment(R.id.flContainer, QuanLiKeHoachFragment())
        }
        tvLogout.setOnClickListener {
            drawerLayoutContain.closeDrawer(GravityCompat.START)
        }
    }

    private fun setTitleToolbar(title: String) {
        actionBar?.title = title
    }
}
