package com.kevinjanvier.ifix.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kevinjanvier.ifix.R
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import com.kevinjanvier.ifix.adapter.CategoryAdapter
import com.kevinjanvier.ifix.model.Categories
import android.widget.Toast




/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var recycler:RecyclerView
    lateinit var searchview:SearchView


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_home, container, false)

        val rowListItem:List<Categories> =getItem()
        val lLayout = GridLayoutManager(activity, 2)

        recycler = view.findViewById(R.id.recycler_view)
        searchview = view.findViewById(R.id.searchview)
        searchview.queryHint =getString(R.string.what_doyou)
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
               // Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show()
                getItem()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
               // Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show()
                return false
            }
        })

       recycler.setHasFixedSize(true)
        recycler.layoutManager = lLayout

        val madapter = CategoryAdapter(activity, rowListItem)
        recycler.adapter = madapter


        return view



    }

    private fun getItem(): List<Categories> {
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


}// Required empty public constructor
