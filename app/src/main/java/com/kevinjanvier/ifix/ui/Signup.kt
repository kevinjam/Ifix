package com.kevinjanvier.ifix.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kevinjanvier.ifix.R

class Signup : AppCompatActivity() {
    lateinit var btnSignUp:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        btnSignUp = findViewById(R.id.btn_sign_up)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }
}
