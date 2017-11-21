package com.kevinjanvier.ifix.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Toast

import com.directions.route.AbstractRouting
import com.directions.route.Route
import com.directions.route.RouteException
import com.directions.route.Routing
import com.directions.route.RoutingListener
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.kevinjanvier.ifix.R
import com.kevinjanvier.ifix.adapter.PlaceAutoCompleteAdapter
import com.kevinjanvier.ifix.help.Util
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Tasks.call
import com.nobrain.android.permissions.AndroidPermissions
import com.nobrain.android.permissions.Checker
import com.nobrain.android.permissions.Result
import java.util.ArrayList

class ShowUserMap : AppCompatActivity(), RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private var map: GoogleMap? = null
    lateinit var start: LatLng
    lateinit var end: LatLng
    lateinit var waypoint: LatLng
    lateinit var starting: AutoCompleteTextView
    lateinit var destination: AutoCompleteTextView
    lateinit var send: ImageView
    lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var progressDialog: ProgressDialog
    private lateinit var polylines: MutableList<Polyline>
    private lateinit var mAdapter: PlaceAutoCompleteAdapter
    val REQUEST_CODE = 102

    /**
     * Load the Map and Display the routes
     */
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        AndroidPermissions.check(this)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .hasPermissions(object : Checker.Action0 {
                    override fun call(permissions: Array<out String>?) {
                        log("______Permission")

                    }

                })

                .noPermissions(object : Checker.Action1 {
                    override fun call(permissions: Array<out String>?) {
                        ActivityCompat.requestPermissions(this@ShowUserMap
                                , Array<String>(1000){Manifest.permission.ACCESS_COARSE_LOCATION
                            Manifest.permission.ACCESS_FINE_LOCATION}
                                , REQUEST_CODE)
                    }

                })

                .check()



        polylines = ArrayList()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
        MapsInitializer.initialize(this)
        mGoogleApiClient.connect()

        var mapFragment: SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment


        starting = findViewById(R.id.start)
        destination = findViewById(R.id.destination)
//        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit()
        }

        map = mapFragment!!.getMapAsync()


        mAdapter = PlaceAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_JAMAICA, null)

        /*
              * Updates the bounds being used by the auto complete adapter based on the position of the
              * map.
              * */
//        map!!.setOnCameraChangeListener {
//            val bounds = map!!.projection.visibleRegion.latLngBounds
//            mAdapter.setBounds(bounds)
//        }

        val center = CameraUpdateFactory.newLatLng(LatLng(18.013610, -77.498803))
        val zoom = CameraUpdateFactory.zoomTo(16f)

//        map!!.moveCamera(center)
//        map!!.animateCamera(zoom)
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0f,
                object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        val center = CameraUpdateFactory.newLatLng(LatLng(location!!.latitude, location.longitude))
                        val zoom = CameraUpdateFactory.zoomTo(16f)

//                            map!!.moveCamera(center)
//                            map!!.animateCamera(zoom)
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    }

                    override fun onProviderEnabled(provider: String?) {
                    }

                    override fun onProviderDisabled(provider: String?) {
                    }

                }
        )
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0f, object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                val center = CameraUpdateFactory.newLatLng(LatLng(location!!.latitude, location.longitude))
                val zoom = CameraUpdateFactory.zoomTo(16f)

//        map!!.moveCamera(center)
//        map!!.animateCamera(zoom)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }

        })

        /*
       * Adds auto complete adapter to both auto complete
       * text views.
       * */
        starting.setAdapter<PlaceAutoCompleteAdapter>(mAdapter)
        destination.setAdapter<PlaceAutoCompleteAdapter>(mAdapter)
        /*
              * Sets the start and destination points based on the values selected
              * from the autocomplete text views.
              * */

//        starting.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            val item = mAdapter.getItem(position)
//            val placeId = item.placeId.toString()
//            log( "Autocomplete item selected: " + item.description)
//
//            /*
//             Issue a request to the Places Geo Data API to retrieve a Place object with additional
//              details about the place.
//              */
//            val placeResult = Places.GeoDataApi
//                    .getPlaceById(mGoogleApiClient, placeId)
//            placeResult.setResultCallback(ResultCallback { places ->
//                if (!places.status.isSuccess) {
//                    // Request did not complete successfully
//                    log( "Place query did not complete. Error: " + places.status.toString())
//                    places.release()
//                    return@ResultCallback
//                }
//                // Get the Place object from the buffer.
//                val place = places.get(0)
//
//                start = place.latLng
//            })
//        }

//        destination.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            val item = mAdapter.getItem(position)
//            val placeId = item.placeId.toString()
//            log( "Autocomplete item selected: " + item.description)
//
//            /*
//             Issue a request to the Places Geo Data API to retrieve a Place object with additional
//              details about the place.
//              */
//            val placeResult = Places.GeoDataApi
//                    .getPlaceById(mGoogleApiClient, placeId)
//            placeResult.setResultCallback(ResultCallback { places ->
//                if (!places.status.isSuccess) {
//                    // Request did not complete successfully
//                    log( "Place query did not complete. Error: " + places.status.toString())
//                    places.release()
//                    return@ResultCallback
//                }
//                // Get the Place object from the buffer.
//                val place = places.get(0)
//
//                end = place.latLng
//            })
//        }

        /*
       These text watchers set the start and end points to null because once there's
       * a change after a value has been selected from the dropdown
       * then the value has to reselected from dropdown to get
       * the correct location.
       * */
        starting.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, startNum: Int, before: Int, count: Int) {
                start
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        destination.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                end
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        send.setOnClickListener({
            sendRequest()
        })

    }

    fun sendRequest() {
        if (Util.Operations.isOnline(this)) {
            route()
        } else {
            Toast.makeText(this, "No internet connectivity", Toast.LENGTH_SHORT).show()
        }
    }

    fun route() {
        if (start == null || end == null) {
            if (start == null) {
                if (starting.text.length > 0) {
                    starting.error = "Choose location from dropdown."
                } else {
                    Toast.makeText(this, "Please choose a starting point.", Toast.LENGTH_SHORT).show()
                }
            }
            if (end == null) {
                if (destination.text.length > 0) {
                    destination.error = "Choose location from dropdown."
                } else {
                    Toast.makeText(this, "Please choose a destination.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            progressDialog = ProgressDialog.show(this, "Please wait.",
                    "Fetching route information.", true)
            val routing = Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(start, end)
                    .build()
            routing.execute()
        }
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
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
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Makerere University"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }
    override fun onRoutingCancelled() {
    }

    override fun onRoutingStart() {
    }

    override fun onRoutingFailure(e: RouteException?) {
        progressDialog.dismiss()
        if (e != null) {
            Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRoutingSuccess(route: ArrayList<Route>, shortestRouteIndex: Int) {
        progressDialog.dismiss()
        val center = CameraUpdateFactory.newLatLng(start)
        val zoom = CameraUpdateFactory.zoomTo(16f)

//        map!!.moveCamera(center)


        if (polylines.size > 0) {
            for (poly in polylines) {
                poly.remove()
            }
        }

        polylines = ArrayList()
        //add route(s) to the map.
        for (i in route.indices) {

            //In case of more than 5 alternative routes
            val colorIndex = i % COLORS.size

            val polyOptions = PolylineOptions()
            polyOptions.color(resources.getColor(COLORS[colorIndex]))
            polyOptions.width((10 + i * 3).toFloat())
            polyOptions.addAll(route[i].points)
            val polyline = map!!.addPolyline(polyOptions)
            polylines.add(polyline)

            Toast.makeText(applicationContext, "Route " + (i + 1) + ": distance - " + route[i].distanceValue + ": duration - " + route[i].durationValue, Toast.LENGTH_SHORT).show()
        }

        // Start marker
        var options = MarkerOptions()
        options.position(start)
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
//        map!!.addMarker(options)

        // End marker
        options = MarkerOptions()
        options.position(end)
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
//        map!!.addMarker(options)
    }

    fun log(msg: String) {
        Log.e(this@ShowUserMap.javaClass.simpleName, msg)
    }

    companion object {
        private val COLORS = intArrayOf(R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light)


        private val BOUNDS_JAMAICA = LatLngBounds(LatLng(-57.965341647205726, 144.9987719580531),
                LatLng(72.77492067739843, -9.998857788741589))
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        AndroidPermissions.result(this@ShowUserMap)
                .addPermissions(REQUEST_CODE, Manifest.permission.ACCESS_COARSE_LOCATION)
                .addPermissions(REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION)
                .putActions(REQUEST_CODE, object : Result.Action0{
                    override fun call() {

                    }

                }, object : Result.Action1{
                    override fun call(hasPermissions: Array<out String>?, noPermissions: Array<out String>?) {

                    }

                })
                .result(requestCode, permissions, grantResults)

    }
}

private fun SupportMapFragment.getMapAsync(): GoogleMap? {
    return null
}
