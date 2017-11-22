package com.kevinjanvier.ifix.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.kevinjanvier.ifix.R
import android.graphics.BitmapFactory
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import com.kevinjanvier.ifix.ui.demosApp.MapMarkerIfix
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*


class CategoryMore : AppCompatActivity() {
    lateinit var mName: TextView
    lateinit var mTitle: TextView
    lateinit var mDesc: TextView
    lateinit var mDesctwo: TextView
    lateinit var toolbar:Toolbar


    lateinit var imageview: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_more)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "User"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        mName = findViewById(R.id.list_item)
        imageview = findViewById(R.id.backdrop)
        mTitle = findViewById(R.id.title)
        mDesc = findViewById(R.id.desc)
        mDesctwo = findViewById(R.id.desctwo)

        mTitle.setOnClickListener {
            val intent = Intent(this, MapMarkerIfix::class.java)
            startActivity(intent)
        }

        fab.setOnClickListener { view ->
            val intent = Intent(this, MapMarkerIfix::class.java)
            startActivity(intent)
        }


        if (!intent.extras.isEmpty){
            val name = intent.extras.getString("username")
            val title = intent.extras.getString("title")
            val desc = intent.extras.getString("desc")
            val desc2 = intent.extras.getString("desc2")


            val photo = intent.extras
            val byteArray = photo.getByteArray("logo")
            val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            log("username $name")
            imageview.setImageBitmap(bmp)

            log("bmp $photo")
            log("title $title")
            log("desc $desc")

            mName.text = name
            mTitle.text = title
            mDesc.text = desc
            mDesctwo.text = desc2

        }
    }

    fun log(msg: String) {
        Log.e(this@CategoryMore.javaClass.simpleName, msg)
    }

}
