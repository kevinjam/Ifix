package com.kevinjanvier.ifix.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.kevinjanvier.ifix.R
import com.kevinjanvier.ifix.help.Constant
import com.kevinjanvier.ifix.help.Utility

class SplashScreen : AppCompatActivity() {
    private lateinit var utility:Utility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        utility = Utility(this@SplashScreen)


        Handler().postDelayed({
            if (utility.getPref(Constant.Islogin) == "true"){

                startActivity(Intent(this@SplashScreen, Dashboard::class.java))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }else{
                startActivity(Intent(this@SplashScreen, MainFirst::class.java))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }


        }, time_out.toLong())
    }

    companion object {
        val time_out = 2000
    }
}
