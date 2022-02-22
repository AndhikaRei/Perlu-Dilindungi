package com.example.perludilindungi

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.perludilindungi.databinding.ActivityQrcodeBinding
import com.example.perludilindungi.model.checkin.CheckInResponse
import com.example.perludilindungi.network.RetrofitClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QRCodeActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityQrcodeBinding

    // Object for scanning qr code.
    private lateinit var codeScanner: CodeScanner
    private var decodeResult: String = ""

    // Object for getting user current location.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // User current latitude and longitude.
    private var latitude: Double = Double.NaN
    private var longitude: Double = Double.NaN

    // Temperature sensor.
    private lateinit var sensorManager : SensorManager
    private var tempSensor : Sensor? = null
    private var temperature : Float = Float.NaN

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check temperature sensor.
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (tempSensor == null){
            Toast.makeText(this, "Temperature sensor is not available",
                Toast.LENGTH_LONG).show()
            binding.roomTemperature.text = "No Temperature Sensor"
        }

        // Check location permission.
        // Ask for permission if permission was denied.
        if(
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_DENIED &&
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQ
            )
        }

        // Check user gps.
        // Ask user to turn on gps if gps wasn't enabled.
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!isGpsOn) {
            Toast.makeText(this,"Please Turn on Your device Location",
                Toast.LENGTH_SHORT).show()
        }

        // Check camera permission.
        // Ask for permission if permission was denied.
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQ)
        } else {
            startScanning()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startScanning() {
        // Get the code scanner.
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)

        codeScanner.apply {
            // Parameters
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
        }

        // Get user current location.
        val cts = CancellationTokenSource()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient
            .getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cts.token)
            .addOnSuccessListener {
                if (it == null){
                    Toast.makeText(this, "Can't get location", Toast.LENGTH_SHORT).show()
                } else {
                    latitude = it.latitude
                    longitude = it.longitude
                }
        }

        // Callbacks
        // Success decode.
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                // Get the decoded string.
                decodeResult = it.text

                // Send the string and location to api.
                RetrofitClient.instance.checkIn(
                    qrCode = decodeResult ,
                    latitude = latitude,
                    longitude = longitude,
                ).enqueue(object: Callback<CheckInResponse>{

                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<CheckInResponse>,
                        response: Response<CheckInResponse>
                    ) {
                        when (response.body()!!.code){
                            200 -> {
                                // Write user status.
                                val userStatus = response.body()!!.data!!.userStatus

                                when (userStatus) {
                                    "green" -> {
                                        binding.userStatus.setBackgroundResource(R.color.green)
                                    }
                                    "yellow" -> {
                                        binding.userStatus.setBackgroundResource(R.color.yellow)
                                    }
                                    "red" -> {
                                        binding.userStatus.setBackgroundResource(R.color.red)
                                    }
                                    "black" -> {
                                        binding.userStatus.setBackgroundResource(R.color.black)
                                    }
                                }

                                // Display graphic based on user status.
                                if (userStatus == "green" || userStatus == "yellow"){
                                    binding.iconStatus.visibility = View.VISIBLE
                                    binding.iconStatus.setImageResource(R.drawable.ic_success_scan)
                                    binding.statusScan.text = "Berhasil"
                                    binding.userReason.text = ""
                                } else {
                                    binding.iconStatus.visibility = View.VISIBLE
                                    binding.iconStatus.setImageResource(R.drawable.ic_failed_scan)
                                    binding.statusScan.text = "Gagal"
                                    // Write user reason.
                                    val userReason = response.body()!!.data!!.reason
                                    binding.userReason.text = userReason
                                }

                            }
                            400 -> {
                                binding.iconStatus.visibility = View.VISIBLE
                                binding.iconStatus.setImageResource(R.drawable.ic_failed_scan)
                                binding.statusScan.text = "Gagal"
                                binding.userStatus.text = "Invalid Qr Code"
                                binding.userReason.text = ""
                            }
                        }
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onFailure(call: Call<CheckInResponse>, t: Throwable) {
                        binding.iconStatus.visibility = View.VISIBLE
                        binding.iconStatus.setImageResource(R.drawable.ic_failed_scan)
                        binding.statusScan.text = "Gagal"
                        binding.userStatus.text = "Android error"
                        binding.userReason.text = ""
                        Log.e("Error", "Check-in failed")
                        t.message?.let { Log.e("Error", it) }
                    }
                })
            }
        }
        // Failed to use code scanner.
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(applicationContext, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the camera permission to use this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            LOCATION_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the location permission to use this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(sensorEvent: SensorEvent) {
        temperature = sensorEvent.values[0]
        binding.roomTemperature.text = "$temperature °C"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        if (tempSensor != null){
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        if (tempSensor != null){
            sensorManager.unregisterListener(this)
        }
        super.onPause()
    }

    companion object {
        const val CAMERA_REQ = 123
        const val LOCATION_REQ = 100
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (decodeResult != ""){
            decodeResult = ""
            binding.iconStatus.visibility = View.INVISIBLE
            binding.userStatus.setBackgroundResource(R.color.transparent)
            binding.statusScan.text = ""
            binding.userReason.text = ""
        }
        return super.dispatchTouchEvent(ev)
    }
}