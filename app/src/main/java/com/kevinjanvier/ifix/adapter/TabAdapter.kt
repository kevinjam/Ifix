package com.kevinjanvier.ifix.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by kevinjanvier on 17/11/2017.
 */
class TabAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mlisttitle = ArrayList<String>()


    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {

        return mlisttitle.size
    }

    fun addfragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mlisttitle.add(title)

    }

    override fun getPageTitle(position: Int): CharSequence {

        return mlisttitle[position]
    }
}