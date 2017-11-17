package com.kevinjanvier.ifix.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.kevinjanvier.ifix.R
import android.graphics.BitmapFactory
import android.widget.ImageView
import de.hdodenhof.circleimageview.CircleImageView


class CategoryMore : AppCompatActivity() {
    lateinit var mName: TextView
    lateinit var mTitle: TextView
    lateinit var mDesc: TextView
    lateinit var mDesctwo: TextView


    lateinit var imageview: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_more)

        mName = findViewById(R.id.list_item)
        imageview = findViewById(R.id.backdrop)
        mTitle = findViewById(R.id.title)
        mDesc = findViewById(R.id.desc)
        mDesctwo = findViewById(R.id.desctwo)

        mTitle.setOnClickListener {
            val intent = Intent(this, ShowUserMap::class.java)
            startActivity(intent)
        }



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



//
//        val name:Bundle = intent.extras
//
//
//        if (name.getSerializable("username") != null){
//
//            log("username $name" )
////            val category:Categories = Categories(name)
//
//
////            title.text = bundle
//
//
//
//
//        }
    }

    fun log(msg: String) {
        Log.e(this@CategoryMore.javaClass.simpleName, msg)
    }
}
