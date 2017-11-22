package com.kevinjanvier.ifix.ui

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.kevinjanvier.ifix.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kevinjanvier.ifix.help.Constant
import com.kevinjanvier.ifix.help.Utility
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog


class Login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    lateinit var btnSign: Button

    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var utility:Utility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        utility = Utility(this@Login)

        mAuth = FirebaseAuth.getInstance()


        btnSign = findViewById(R.id.btn_sign_in)
        username = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)

        btnSign.setOnClickListener {

            val dialog = indeterminateProgressDialog(message = "Please wait…", title = "User Login")

            val username: String = username.text.toString()
            val password: String = password.text.toString()
            if (username.isNullOrEmpty()) {
                dialog.dismiss()
                alert("Please Enter Username", "Attention") {
                    yesButton {}
                    noButton {}
                }.show()

            } else if (password.isNullOrEmpty()) {
                dialog.dismiss()
                alert("Please Enter Password", "Attention") {
                    yesButton {}
                    noButton {}
                }.show()
            } else {
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                dialog.dismiss()
                                log("signInWithEmail:success")
                                val intent = Intent(this, Dashboard::class.java)
                                val fcmUser: FirebaseUser = mAuth.currentUser!!
                                utility.savePref(Constant.Islogin, "true")
                                utility.savePref(Constant.email, fcmUser.email!!)
                                log("User : " + fcmUser.email)
                                startActivity(intent)
                            } else {
                                dialog.dismiss()
                                alert("${task.exception}", "Failure") {
                                    yesButton { }
                                    noButton {}
                                }.show()
                                log("signInWithEmail:failure " + task.exception)
                            }
                        }
            }


        }


    }

    override fun onStart() {
        super.onStart()
        //check if user is signed in (


    }

    fun log(msg: String) {
        Log.e(this@Login.javaClass.simpleName, msg)
    }
}
