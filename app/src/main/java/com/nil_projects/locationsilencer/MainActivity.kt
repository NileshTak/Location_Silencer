package com.nil_projects.locationsilencer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.AudioManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.RemoteException
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_list_view.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import android.support.v4.content.ContextCompat.getSystemService
import android.app.NotificationManager


class MainActivity : AppCompatActivity() {

    val context = this
    var db = databaseHandler(context)

    var arrList = arrayOf("Save Location","Recent Activities")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        silencerMethod()

//        circle_menu_button.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.main1,R.drawable.main)
//                .addSubMenu(Color.parseColor("#25BCFE"),R.drawable.sub1)
//                .addSubMenu(Color.parseColor("#6D4C41"),R.drawable.sub2)
//                .setOnMenuSelectedListener {
//                    index -> Toast.makeText(this,"Selected" +arrList[index],Toast.LENGTH_SHORT).show()
//
//                    when(arrList[index])
//                    {
//                        "Save Location" -> {
//                            getUserLocation()
//                        }
//                        "Recent Activities" -> {
//                             Thread{
//                                var intent = Intent(applicationContext,list_view :: class.java)
//                                startActivity(intent)
//                            }.start()
//
//                        }
//                    }
//                }

        btn_saveLoc.setOnClickListener {
            getUserLocation()
        }
        btn_records.setOnClickListener {
            var intent = Intent(applicationContext,list_view :: class.java)
            startActivity(intent)
        }
    }

    var AccessLocation = 123
    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat
                            .checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), AccessLocation)
                return
            }
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted) {

            val intent = Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivity(intent)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            AccessLocation -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "GPS Permission ALready Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "User Not Granted Permission", Toast.LENGTH_SHORT).show()

                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("MissingPermission")
    private fun silencerMethod() {
        var myLocation = MyLocationListenerClassFinal()
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 3f, myLocation)
    }

    var locationUserFinal: Location? = null

    inner class MyLocationListenerClassFinal : LocationListener {

        override fun onLocationChanged(location: Location?)  {
            locationUserFinal = location
            //      Toast.makeText(applicationContext,locationUserFinal!!.latitude.toString(),Toast.LENGTH_LONG).show()
            var data = db.readData()
            var am : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            for (i in 0..(data.size - 1)) {

                Toast.makeText(applicationContext,data[i].Name.toString(),Toast.LENGTH_LONG).show()
                if(data[i].Longitude.toString() == locationUserFinal!!.longitude.toString()
                        && data[i].Latitude.toString() == locationUserFinal!!.latitude.toString())
                {
                    Toast.makeText(applicationContext,"Detected",Toast.LENGTH_LONG).show()

                    am.ringerMode = AudioManager.RINGER_MODE_SILENT
                    //   tv_result.text = locationUserFinal!!.longitude.toString()
                }
                else
                {
                    Toast.makeText(applicationContext,"Not Detected",Toast.LENGTH_LONG).show()
                    am.ringerMode = AudioManager.RINGER_MODE_NORMAL
                }
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

    }


    @SuppressLint("MissingPermission")
   private fun getUserLocation() {
        var myLocation = MyLocationListenerClass()
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3f, myLocation)
    }

    var locationUser: Location? = null

    inner class MyLocationListenerClass : LocationListener {

        override fun onLocationChanged(location: Location?) {
            locationUser = location
            //    tv_result.append("Latitude is " +locationUser!!)

            var intent = Intent(applicationContext, RegisterSilencer::class.java)
           // tv_result.text = locationUser!!.latitude.toString()
            intent.putExtra("longitude_key", locationUser!!.longitude.toString())
            intent.putExtra("latitude_key", locationUser!!.latitude.toString())
            startActivity(intent)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

    }
}