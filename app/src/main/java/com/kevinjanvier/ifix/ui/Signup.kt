package com.kevinjanvier.ifix.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kevinjanvier.ifix.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog

class Signup : AppCompatActivity() {
    lateinit var btnSignUp: Button
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var mAuth: FirebaseAuth
    lateinit var goSign: LinearLayout

    lateinit var FirstName: EditText
    lateinit var Username: EditText
    lateinit var iv_back:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_password)

        FirstName = findViewById(R.id.et_firstname)
        Username = findViewById(R.id.et_username)


        btnSignUp = findViewById(R.id.btn_sign_up)
        goSign = findViewById(R.id.go_signin)

        iv_back = findViewById(R.id.iv_back)
        iv_back.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


        btnSignUp.setOnClickListener {
            val dialog = indeterminateProgressDialog(message = "Please wait…", title = "User Login")


            //            val intent = Intent(this, Dashboard::class.java)
//            startActivity(intent)
            val email: String = email.text.toString()
            val password: String = password.text.toString()
            val fname: String = FirstName.text.toString()
            val username: String = Username.text.toString()

            log("Email $email" + "password : $password")
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            dialog.dismiss()
                            log("createUserWithEmail:success")
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)

                            val user: FirebaseUser = mAuth.currentUser!!
                            log("User $user")

                        } else {
                            dialog.dismiss()

                            alert("${task.exception}", "Registration Error") {
                                yesButton { }
                                noButton {}
                            }.show()
                            log("createUserWithEmail:failure" + task.exception)


                        }
                    }
        }

        goSign.setOnClickListener({
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)
        })
    }


    fun log(msg: String) {
        Log.e(this@Signup.javaClass.simpleName, msg)
    }
}

