package com.kevinjanvier.ifix.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kevinjanvier.ifix.R

class Login : AppCompatActivity() {

    lateinit var btnSign:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnSign = findViewById(R.id.btn_sign_in)

        btnSign.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }
}
