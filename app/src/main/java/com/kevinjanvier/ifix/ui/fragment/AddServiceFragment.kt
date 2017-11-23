package com.kevinjanvier.ifix.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.kevinjanvier.ifix.R
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.progressDialog
import org.jetbrains.anko.textView


/**
 * A simple [Fragment] subclass.
 */
class AddServiceFragment : Fragment() {

    lateinit var btnSend:Button


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater!!.inflate(R.layout.fragment_add, container, false)

        btnSend = view.findViewById(R.id.btnSend)

        btnSend.setOnClickListener{

            alert("We shall review your Request") {
                activity.title = "Submitted"
                yesButton {
                    val intent = Intent(activity, HomeFragment::class.java)
                    startActivity(intent)
                }
                noButton { }
            }.show()

        }

        return view
    }

}// Required empty public constructor
