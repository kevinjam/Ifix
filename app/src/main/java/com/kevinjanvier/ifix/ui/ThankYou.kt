package com.kevinjanvier.ifix.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.kevinjanvier.ifix.R

class ThankYou : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)
        Handler().postDelayed({

            startActivity(Intent(this@ThankYou, Dashboard::class.java))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            finish()
        }, time_out.toLong())
    }

    companion object {
        val time_out = 2000
    }
    }

