package com.kevinjanvier.ifix.ui.fragment


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.kevinjanvier.ifix.R
import com.kevinjanvier.ifix.ui.Dashboard
import com.kevinjanvier.ifix.ui.demosApp.MapMarkerIfix
import org.jetbrains.anko.support.v4.alert


/**
 * A simple [Fragment] subclass.
 */
class AddServiceFragment : Fragment() {

    private lateinit var btnSend: Button


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_add, container, false)

        btnSend = view.findViewById(R.id.btnSend)

        btnSend.setOnClickListener {

            showNewNameDialog()

        }

        return view
    }

    fun showNewNameDialog() {
        val dialogBuilder = AlertDialog.Builder(activity)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)
        dialogBuilder.setView(dialogView)


        dialogBuilder.setTitle("Success")
        dialogBuilder.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
            val intent = Intent(activity, Dashboard::class.java)
            startActivity(intent)

        })
        dialogBuilder.setNegativeButton("", { dialog, whichButton ->

            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }


}
