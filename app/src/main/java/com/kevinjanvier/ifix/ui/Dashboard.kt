package com.kevinjanvier.ifix.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.kevinjanvier.ifix.R
import com.kevinjanvier.ifix.adapter.TabAdapter
import com.kevinjanvier.ifix.ui.fragment.*

class Dashboard : AppCompatActivity() {

    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager
    lateinit var toolbar: Toolbar

    private var tabIcon:IntArray = intArrayOf(R.drawable.ic_home_black_24dp,
            R.drawable.ic_search_black_24dp,R.drawable.ic_add_circle_black_24dp,
            R.drawable.ic_people_black_24dp,R.drawable.ic_user)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        viewpager = findViewById(R.id.viewpager)
        setupViewpager(viewpager)

        tablayout = findViewById(R.id.tabs)
        tablayout.setupWithViewPager(viewpager)
        setupIcons()
    }

    private fun setupIcons(){
        tablayout.getTabAt(0)!!.setIcon(tabIcon[0])
        tablayout.getTabAt(1)!!.setIcon(tabIcon[1])
        tablayout.getTabAt(2)!!.setIcon(tabIcon[2])
        tablayout.getTabAt(3)!!.setIcon(tabIcon[3])
//        tablayout.getTabAt(4)!!.setIcon(tabIcon[4])


    }

    private fun setupViewpager(viewpager: ViewPager?) {
        val adapter = TabAdapter(supportFragmentManager)
        adapter.addfragment(HomeFragment(), "Home")
//        adapter.addfragment(SearchFragment(), "Search")
        adapter.addfragment(AddServiceFragment(), "Add Item")
        adapter.addfragment(AboutFragment(), "About")
        adapter.addfragment(ProfileFragment(), "Profile")
        viewpager!!.adapter = adapter

    }


}
