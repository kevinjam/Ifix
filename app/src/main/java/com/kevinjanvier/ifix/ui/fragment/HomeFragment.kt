package com.kevinjanvier.ifix.ui.fragment


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kevinjanvier.ifix.R
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import com.kevinjanvier.ifix.model.Categories
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kevinjanvier.ifix.ui.CategoryMore
import java.io.ByteArrayOutputStream
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var recycler:RecyclerView
//    lateinit var searchview:SearchView

    private lateinit var dictionaryWords: List<Categories>
    private lateinit var filteredList: MutableList<Categories>
    private lateinit var mAdapter: SimpleItemRecyclerViewAdapter
    private lateinit var searchview: EditText


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_home, container, false)


        // retrieve users
        val users : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        if (users != null){
            val email:String = users.email!!

            log("Email =----- " + email)
        }

        dictionaryWords = listItemData
        filteredList = java.util.ArrayList()
        filteredList.addAll(dictionaryWords)
        val lLayout = GridLayoutManager(activity, 2)
        recycler = view.findViewById(R.id.recycler_view)
       // val rowListItem:List<Categories> =getItem()
        recycler.layoutManager = lLayout

        searchview = view.findViewById(R.id.search_box)

//        recycler.setHasFixedSize(true)

        mAdapter = SimpleItemRecyclerViewAdapter(filteredList)
        recycler.adapter = mAdapter

        searchview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAdapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })


//
//        val madapter = CategoryAdapter(activity, rowListItem)
//        recycler.adapter = madapter


        return view



    }


    // create a custom RecycleViewAdapter class
    inner class SimpleItemRecyclerViewAdapter(private val mValues: List<Categories>) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>(), Filterable {
        private val mFilter: CustomFilter

        init {
            mFilter = CustomFilter(this@SimpleItemRecyclerViewAdapter)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mItem = mValues[position]
            holder.mIdView.text = mValues[position].name.toString()
            holder.PersonPhoto.setImageResource(mValues[position].photo)

           holder.listItem.setOnClickListener {
               val intent = Intent(context, CategoryMore::class.java)
               intent.putExtra("username", mValues[position].name)
               intent.putExtra("title", mValues[position].categorytitle)
               intent.putExtra("desc", mValues[position].desc)
               intent.putExtra("desc2", mValues[position].dectwo)


               val bmp = BitmapFactory.decodeResource(context.resources, mValues[position].photo)
               val stream = ByteArrayOutputStream()
               bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
               val byteArray = stream.toByteArray()

               intent.putExtra("logo", byteArray)
               context.startActivity(intent)
           }

        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        override fun getFilter(): Filter {
            return mFilter
        }

        inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView = mView.findViewById(R.id.country_name)
            val PersonPhoto: ImageView = itemView.findViewById(R.id.country_photo)
           var listItem :LinearLayout =itemView.findViewById(R.id.list_item)


            var mItem: Categories? = null

            override fun toString(): String {
                return super.toString() + " '" + mIdView.text + "'"
            }
        }

        inner class CustomFilter (private val mAdapter: SimpleItemRecyclerViewAdapter) : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                filteredList!!.clear()
                val results = Filter.FilterResults()
                if (constraint.length == 0) {
                    filteredList!!.addAll(dictionaryWords!!)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                    for (mWords in dictionaryWords!!) {
                        if (mWords.name!!.toLowerCase().startsWith(filterPattern)) {
                            filteredList!!.add(mWords)
                        }
                    }
                }
                println("Count Number " + filteredList!!.size)
                results.values = filteredList
                results.count = filteredList!!.size
                return results
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                println("Count Number 2 " + (results.values as List<Categories>).size)
                this.mAdapter.notifyDataSetChanged()
            }
        }
    }


    private val listItemData: List<Categories> get() {
        val addArrayList = ArrayList<Categories>()
        addArrayList.add(Categories("Book Ticket", R.drawable.ticket, "Concert Ticket", "Movie Ticket", "Travel Ticket"))
        addArrayList.add(Categories("Transport", R.drawable.trucking,"dumy", "dumy", "Travel Ticket"))
        addArrayList.add(Categories("Help", R.drawable.ic_help,"dumy", "dumy", "Travel Ticket"))
        addArrayList.add(Categories("Entertainment", R.drawable.juggler,"dumy", "dumy", "Travel Ticket"))
        addArrayList.add(Categories("Shopping", R.drawable.cart,"dumy", "dumy", "Travel Ticket"))
        addArrayList.add(Categories("Flexing", R.drawable.flexing,"dumy", "dumy", "Travel Ticket"))
        addArrayList.add(Categories("Cars", R.drawable.car,"dumy", "dumy", "Travel Ticket"))
        addArrayList.add(Categories("Cinemae", R.drawable.popcorn,"dumy", "dumy", "Travel Ticket"))

        return addArrayList
    }
    fun log(msg: String) {
        Log.e(activity.javaClass.simpleName, msg)
    }

}// Required empty public constructor
