package com.kevinjanvier.ifix.help

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by kevinjanvier on 22/11/2017.
 */

class Utility {

    private var  mContext: Context? = null
    private var mSharedPreferences: SharedPreferences?= null
    private var edit: SharedPreferences.Editor?= null


    @SuppressLint("CommitPrefEdits")
    constructor(context: Context) {
        mContext = context
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        edit = mSharedPreferences!!.edit()

    }

    constructor()

    fun savePref(key: String, value: String) {
        mSharedPreferences!!.edit().putString(key, value).apply()

    }


    fun getPref(key: String, vararg defValue: String): String? {
        return if (defValue.isNotEmpty())
            mSharedPreferences!!.getString(key, defValue[0])
        else
            mSharedPreferences!!.getString(key, "")

    }

    companion object {
        private val PREFERENCE_NAME = "out_there"
    }

}
