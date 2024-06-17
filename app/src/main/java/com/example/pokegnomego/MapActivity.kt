package com.example.pokegnomego

import android.Manifest
import android.R.attr.country
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.pokegnomego.models.Visit
import com.example.pokegnomego.network.ApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val permissionCode = 101
    private var gnome_id: Int = -1
    private var user_id: Int = -1
    private lateinit var WroclawMap: GoogleMap
    val TAG = "MapActivity"





    val REQUEST_CAMERA_PERMISSION = 1001
    private val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 1002
    private val REQUEST_IMAGE_CAPTURE = 1003
    private val REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 1004
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION
            )
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_EXTERNAL_STORAGE_PERMISSION
            )
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION || requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private lateinit var photoFile: File
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_",".jpg",storageDir).apply {
            photoFile = this
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        checkPermissions()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val back = findViewById<Button>(R.id.button_back)
        back.setOnClickListener {
            val i = Intent(this@MapActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        val photoButton = findViewById<Button>(R.id.button_photo)
        photoButton.setOnClickListener {
            dispatchTakePictureIntent()

        }

        locationRequest = LocationRequest.create().apply {
            interval = 10000 // Request location update every 10 seconds
            fastestInterval = 500 // The fastest interval for location updates, 5 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        //update the location after obtained
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Log.d("update", location.latitude.toString())
                    val latLng = LatLng(location.latitude, location.longitude)
                    WroclawMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                    WroclawMap.addMarker(MarkerOptions().position(latLng))
                }
            }
        }

    }

    override fun onMapReady(MyMap: GoogleMap) {
        WroclawMap = MyMap
        val latLng = LatLng(51.11054553520132, 17.03326405469526)
        WroclawMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        val sharedPreferences = getSharedPreferences("CoordsPrefs", Context.MODE_PRIVATE)
        val latitude = sharedPreferences.getString("Latitude", null)?.toDoubleOrNull()
        val longitude = sharedPreferences.getString("Longitude", null)?.toDoubleOrNull()
        if (latitude != null && longitude != null) {
            val location = LatLng(latitude, longitude)
            WroclawMap.addMarker(MarkerOptions().position(location).title("Wylosowany krasnal"))
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager).also {
                val photoURI: Uri = FileProvider.getUriForFile(this,
                    "com.example.pokegnomego.fileprovider",createImageFile())
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }

            // Photo captured successfully, you can now update the gallery
            updateGallery(photoFile)


            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        Log.d(TAG, "Location obtained: Latitude = $latitude, Longitude = $longitude")
                        sendVisitToServer(latitude, longitude)
                    }

                }
            }
        }
    }

    fun updateGallery(photoFile: File) {
        MediaScannerConnection.scanFile(this, arrayOf(photoFile.absolutePath), null) { _, uri ->

        }
        Log.d(TAG, "Updating gallery with photo file: ${photoFile.absolutePath}")
    }
    fun sendVisitToServer(latitude: Double, longitude: Double) {

        val visit_date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val sharedDate = getSharedPreferences("CoordsPrefs", Context.MODE_PRIVATE)
        gnome_id = sharedDate.getInt("gnome_id", -1)
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        user_id = sharedPreferences.getInt("userId", -1)
        Log.d(TAG, "Sending visit to server with Latitude = $latitude, Longitude = $longitude, $user_id, $gnome_id, $visit_date ")




        val visit = Visit(user_id, gnome_id, visit_date, latitude, longitude)
        ApiClient.apiService.addVisit(visit).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MapActivity, "Dodano wizytę", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MapActivity, "Nie dodano wizyty", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@MapActivity, "Nie dodano wizyty", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to fetch the last known location
    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        // Log the latitude and longitude
                        Log.d("Location", "Latitude: " + location.latitude)
                        Log.d("Location", "Longitude: " + location.longitude)

                        // Use Geocoder to get detailed location information
                            // Log detailed location information )
                        }


                }
        } else {

            return
        }
    }

}