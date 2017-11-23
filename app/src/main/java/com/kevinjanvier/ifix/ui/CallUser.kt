package com.kevinjanvier.ifix.ui

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kevinjanvier.ifix.R
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.widget.ImageView
import android.widget.TextView
import com.kevinjanvier.ifix.help.Utility
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import com.kevinjanvier.ifix.ui.demosApp.MapMarkerIfix

class CallUser : AppCompatActivity() {
    private lateinit var buttonCall: ImageView
    private lateinit var username: TextView
    private lateinit var userPhone: TextView
    lateinit var btnCont: Button
    private lateinit var utility: Utility
    lateinit var toolbar:Toolbar
    var phoneNumber:String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_user)

        utility = Utility(this@CallUser)

        username = findViewById(R.id.user_profile_name)
        userPhone = findViewById(R.id.et_phoneNumber)
        toolbar = findViewById(R.id.toolbar)
        btnCont = findViewById(R.id.btn_continue)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "User"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setOnClickListener {
            val intent = Intent(this, MapMarkerIfix::class.java)
            startActivity(intent)
        }




        val fname = intent.extras.getString("Person")
        phoneNumber = intent.extras.getString("PhoneNumber")
        username.text = fname
        userPhone.text = "Telephone : $phoneNumber"

        buttonCall = findViewById(R.id.add_friend)

        buttonCall.setOnClickListener {
            //            val callIntent = Intent(Intent.ACTION_CALL)
//            callIntent.data = Uri.parse("tel:+256785077853")
//            if (ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            }
//            startActivity(callIntent)
            onCall(phoneNumber!!)

        }

        btnCont.setOnClickListener {
            oncallScreen()
        }
    }

    private fun onCall(phone:String) {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    123)
        } else {
            startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:$phone")))
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            123 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onCall(phoneNumber!!)
            } else {
                Log.d("TAG", "Call Permission Not Granted")
            }

            else -> {
            }
        }
    }


    private fun oncallScreen() {
        startActivity(Intent(this@CallUser, ThankYou::class.java))


    }
}
