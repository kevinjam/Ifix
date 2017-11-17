//package com.kevinjanvier.ifix.ui
//
//import android.app.ProgressDialog
//import android.location.Location
//import android.location.LocationListener
//import android.location.LocationManager
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.view.View
//import android.widget.AdapterView
//import android.widget.AutoCompleteTextView
//import android.widget.ImageView
//import android.widget.Toast
//
//import com.directions.route.AbstractRouting
//import com.directions.route.Route
//import com.directions.route.RouteException
//import com.directions.route.Routing
//import com.directions.route.RoutingListener
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.common.api.PendingResult
//import com.google.android.gms.common.api.ResultCallback
//import com.google.android.gms.location.places.Place
//import com.google.android.gms.location.places.PlaceBuffer
//import com.google.android.gms.location.places.Places
//import com.google.android.gms.maps.CameraUpdate
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.MapsInitializer
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.LatLngBounds
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.gms.maps.model.Polyline
//import com.google.android.gms.maps.model.PolylineOptions
//
//import java.util.ArrayList
//
///**
// * Created by kevinjanvier on 17/11/2017.
// */
//
//class MainActivity : AppCompatActivity(), RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
//    protected var map: GoogleMap
//    protected var start: LatLng? = null
//    protected var end: LatLng? = null
//    @InjectView(R.id.start)
//    internal var starting: AutoCompleteTextView? = null
//    @InjectView(R.id.destination)
//    internal var destination: AutoCompleteTextView? = null
//    @InjectView(R.id.send)
//    internal var send: ImageView? = null
//    protected var mGoogleApiClient: GoogleApiClient
//    private var mAdapter: PlaceAutoCompleteAdapter? = null
//    private var progressDialog: ProgressDialog? = null
//    private var polylines: MutableList<Polyline>? = null
//
//    /**
//     * This activity loads a map and then displays the route and pushpins on it.
//     */
//    public override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        ButterKnife.inject(this)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//
//        polylines = ArrayList()
//        mGoogleApiClient = GoogleApiClient.Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build()
//        MapsInitializer.initialize(this)
//        mGoogleApiClient.connect()
//
//        var mapFragment: SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//
//        if (mapFragment == null) {
//            mapFragment = SupportMapFragment.newInstance()
//            supportFragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit()
//        }
//        map = mapFragment!!.map
//
//        mAdapter = PlaceAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
//                mGoogleApiClient, BOUNDS_JAMAICA, null)
//
//
//        /*
//        * Updates the bounds being used by the auto complete adapter based on the position of the
//        * map.
//        * */
//        map.setOnCameraChangeListener {
//            val bounds = map.projection.visibleRegion.latLngBounds
//            mAdapter!!.setBounds(bounds)
//        }
//
//
//        val center = CameraUpdateFactory.newLatLng(LatLng(18.013610, -77.498803))
//        val zoom = CameraUpdateFactory.zoomTo(16f)
//
//        map.moveCamera(center)
//        map.animateCamera(zoom)
//
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//        locationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER, 5000, 0f,
//                object : LocationListener {
//                    override fun onLocationChanged(location: Location) {
//
//                        val center = CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude))
//                        val zoom = CameraUpdateFactory.zoomTo(16f)
//
//                        map.moveCamera(center)
//                        map.animateCamera(zoom)
//                    }
//
//                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
//
//                    }
//
//                    override fun onProviderEnabled(provider: String) {
//
//                    }
//
//                    override fun onProviderDisabled(provider: String) {
//
//                    }
//                })
//
//
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                3000, 0f, object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                val center = CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude))
//                val zoom = CameraUpdateFactory.zoomTo(16f)
//
//                map.moveCamera(center)
//                map.animateCamera(zoom)
//
//            }
//
//            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
//
//            }
//
//            override fun onProviderEnabled(provider: String) {
//
//            }
//
//            override fun onProviderDisabled(provider: String) {
//
//            }
//        })
//
//
//        /*
//        * Adds auto complete adapter to both auto complete
//        * text views.
//        * */
//        starting!!.setAdapter(mAdapter)
//        destination!!.setAdapter(mAdapter)
//
//
//        /*
//        * Sets the start and destination points based on the values selected
//        * from the autocomplete text views.
//        * */
//
//        starting!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            val item = mAdapter!!.getItem(position)
//            val placeId = String.valueOf(item.placeId)
//            Log.i(LOG_TAG, "Autocomplete item selected: " + item.description)
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
//                    Log.e(LOG_TAG, "Place query did not complete. Error: " + places.status.toString())
//                    places.release()
//                    return@ResultCallback
//                }
//                // Get the Place object from the buffer.
//                val place = places.get(0)
//
//                start = place.latLng
//            })
//        }
//        destination!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            val item = mAdapter!!.getItem(position)
//            val placeId = String.valueOf(item.placeId)
//            Log.i(LOG_TAG, "Autocomplete item selected: " + item.description)
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
//                    Log.e(LOG_TAG, "Place query did not complete. Error: " + places.status.toString())
//                    places.release()
//                    return@ResultCallback
//                }
//                // Get the Place object from the buffer.
//                val place = places.get(0)
//
//                end = place.latLng
//            })
//        }
//
//        /*
//        These text watchers set the start and end points to null because once there's
//        * a change after a value has been selected from the dropdown
//        * then the value has to reselected from dropdown to get
//        * the correct location.
//        * */
//        starting!!.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence, startNum: Int, before: Int, count: Int) {
//                if (start != null) {
//                    start = null
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {
//
//            }
//        })
//
//        destination!!.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//
//
//                if (end != null) {
//                    end = null
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {
//
//            }
//        })
//
//    }
//
//    @OnClick(R.id.send)
//    fun sendRequest() {
//        if (Util.Operations.isOnline(this)) {
//            route()
//        } else {
//            Toast.makeText(this, "No internet connectivity", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun route() {
//        if (start == null || end == null) {
//            if (start == null) {
//                if (starting!!.text.length > 0) {
//                    starting!!.error = "Choose location from dropdown."
//                } else {
//                    Toast.makeText(this, "Please choose a starting point.", Toast.LENGTH_SHORT).show()
//                }
//            }
//            if (end == null) {
//                if (destination!!.text.length > 0) {
//                    destination!!.error = "Choose location from dropdown."
//                } else {
//                    Toast.makeText(this, "Please choose a destination.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else {
//            progressDialog = ProgressDialog.show(this, "Please wait.",
//                    "Fetching route information.", true)
//            val routing = Routing.Builder()
//                    .travelMode(AbstractRouting.TravelMode.DRIVING)
//                    .withListener(this)
//                    .alternativeRoutes(true)
//                    .waypoints(start, end)
//                    .build()
//            routing.execute()
//        }
//    }
//
//
//    override fun onRoutingFailure(e: RouteException?) {
//        // The Routing request failed
//        progressDialog!!.dismiss()
//        if (e != null) {
//            Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onRoutingStart() {
//        // The Routing Request starts
//    }
//
//    fun onRoutingSuccess(route: List<Route>, shortestRouteIndex: Int) {
//        progressDialog!!.dismiss()
//        val center = CameraUpdateFactory.newLatLng(start)
//        val zoom = CameraUpdateFactory.zoomTo(16f)
//
//        map.moveCamera(center)
//
//
//        if (polylines!!.size > 0) {
//            for (poly in polylines!!) {
//                poly.remove()
//            }
//        }
//
//        polylines = ArrayList()
//        //add route(s) to the map.
//        for (i in route.indices) {
//
//            //In case of more than 5 alternative routes
//            val colorIndex = i % COLORS.size
//
//            val polyOptions = PolylineOptions()
//            polyOptions.color(resources.getColor(COLORS[colorIndex]))
//            polyOptions.width((10 + i * 3).toFloat())
//            polyOptions.addAll(route[i].points)
//            val polyline = map.addPolyline(polyOptions)
//            polylines!!.add(polyline)
//
//            Toast.makeText(applicationContext, "Route " + (i + 1) + ": distance - " + route[i].distanceValue + ": duration - " + route[i].durationValue, Toast.LENGTH_SHORT).show()
//        }
//
//        // Start marker
//        var options = MarkerOptions()
//        options.position(start)
//        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
//        map.addMarker(options)
//
//        // End marker
//        options = MarkerOptions()
//        options.position(end)
//        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
//        map.addMarker(options)
//
//    }
//
//    override fun onRoutingCancelled() {
//        Log.i(LOG_TAG, "Routing was cancelled.")
//    }
//
//    override fun onConnectionFailed(connectionResult: ConnectionResult) {
//
//        Log.v(LOG_TAG, connectionResult.toString())
//    }
//
//    override fun onConnected(bundle: Bundle?) {}
//
//    override fun onConnectionSuspended(i: Int) {
//
//    }
//
//    companion object {
//        private val LOG_TAG = "MyActivity"
//        private val COLORS = intArrayOf(R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light)
//
//
//        private val BOUNDS_JAMAICA = LatLngBounds(LatLng(-57.965341647205726, 144.9987719580531),
//                LatLng(72.77492067739843, -9.998857788741589))
//    }
//}
