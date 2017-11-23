package com.kevinjanvier.ifix.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.kevinjanvier.ifix.R
import com.kevinjanvier.ifix.help.Constant
import com.kevinjanvier.ifix.help.Utility
import com.kevinjanvier.ifix.ui.Login
import com.kevinjanvier.ifix.ui.demosApp.MapMarkerIfix


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    lateinit var utility:Utility


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View =  inflater!!.inflate(R.layout.fragment_profile, container, false)

        utility = Utility(activity)

        val user_email:TextView = view.findViewById(R.id.et_email)
        val logout:ImageView = view.findViewById(R.id.btn_logout)

        // retrieve users
        val users : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        if (users != null){
            user_email.text = users.email
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            utility.savePref(Constant.Islogin, "false")
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
        }
        return view
    }

    fun log(msg:String){
        Log.e(this@ProfileFragment.javaClass.simpleName, msg)
    }

}
