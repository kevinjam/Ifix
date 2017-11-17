package com.kevinjanvier.ifix.adapter

/**
 * Created by kevinjanvier on 17/11/2017.
 */
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.kevinjanvier.ifix.R
import com.kevinjanvier.ifix.model.Categories
import com.kevinjanvier.ifix.ui.CategoryMore
import android.graphics.Bitmap
import com.kevinjanvier.ifix.R.mipmap.ic_launcher
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream


class CategoryAdapter(private val context: Context, private val itemList: List<Categories>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, null)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.personName.text = itemList[position].name
        holder.PersonPhoto.setImageResource(itemList[position].photo)


        holder.listItem.setOnClickListener {
            val intent = Intent(context, CategoryMore::class.java)
            intent.putExtra("username",itemList[position].name)
            intent.putExtra("title", itemList[position].categorytitle)
            intent.putExtra("desc", itemList[position].desc)
            intent.putExtra("desc2", itemList[position].dectwo)


            val bmp = BitmapFactory.decodeResource(context.resources, itemList[position].photo)
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            intent.putExtra("logo", byteArray)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return this.itemList.size
    }

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var personName: TextView
        var PersonPhoto: ImageView
        lateinit var listItem :LinearLayout

        init {
            itemView.setOnClickListener(this)
            personName = itemView.findViewById(R.id.country_name)
            PersonPhoto = itemView.findViewById(R.id.country_photo)
            listItem = itemView.findViewById(R.id.list_item)
        }

        override fun onClick(view: View) {


        }
    }
}