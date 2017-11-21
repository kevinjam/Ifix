package com.kevinjanvier.ifix.ui

import android.Manifest
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kevinjanvier.ifix.R
import com.kevinjanvier.ifix.databinding.ActivityCallUserBinding
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat


class CallUser : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding: ActivityCallUserBinding  = DataBindingUtil.setContentView(this, R.layout.activity_call_user)

       binding.buttonCall.setOnClickListener{
           val callIntent = Intent(Intent.ACTION_CALL)
           callIntent.data = Uri.parse("tel:+256785077853")
           if (ActivityCompat.checkSelfPermission(this,
                   Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
           }
           startActivity(callIntent);
       }
    }
}
