package com.kevinjanvier.ifix.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.directions.route.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kevinjanvier.ifix.R
import java.util.ArrayList

class ShowUserMap : AppCompatActivity(), OnMapReadyCallback, RoutingListener {


    private lateinit var mMap: GoogleMap
    lateinit var start:LatLng
    lateinit var end:LatLng
    lateinit var waypoint:LatLng
//    private val BOUNDS_JAMAICA = LatLngBounds(LatLng(-57.965341647205726, 144.9987719580531),
//            LatLng(72.77492067739843, -9.998857788741589))
//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        start = LatLng(18.015365, -77.499382)
        waypoint = LatLng(18.01455, -77.499333)
        end = LatLng(18.012590, -77.500659)

        val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .waypoints(start, waypoint, end)
                .build()
        routing.execute()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }





    override fun onRoutingCancelled() {
    }

    override fun onRoutingStart() {
    }

    override fun onRoutingFailure(p0: RouteException?) {
    }

    override fun onRoutingSuccess(p0: ArrayList<Route>?, p1: Int) {
    }
}
