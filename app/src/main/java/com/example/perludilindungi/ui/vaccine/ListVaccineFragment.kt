package com.example.perludilindungi.ui.vaccine

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.databinding.FragmentListVaccineBinding
import com.example.perludilindungi.model.city.City
import com.example.perludilindungi.model.city.CityResponse
import com.example.perludilindungi.model.faskes.Faskes
import com.example.perludilindungi.model.faskes.FaskesResponse
import com.example.perludilindungi.model.province.Province
import com.example.perludilindungi.model.province.ProvinceResponse
import com.example.perludilindungi.network.RetrofitClient
import com.example.perludilindungi.ui.news.ListVaccineAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListVaccineFragment : Fragment() {

    private var _binding: FragmentListVaccineBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // The recycler view and the layout manager of the recycler view.
    private lateinit var recyclerView: RecyclerView

    // The dropdown attribute.
    private lateinit var dropdownProvince: AutoCompleteTextView
    private lateinit var dropdownCity: AutoCompleteTextView

    // Object for getting user current location.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // User current latitude and longitude.
    private var latitude: Double = Double.NaN
    private var longitude: Double = Double.NaN

    companion object {
        const val LOCATION_REQ = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check location permission.
        // Ask for permission if permission was denied.
        if(
            ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_DENIED &&
            ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQ
            )
        }

        // Check user gps.
        // Ask user to turn on gps if gps wasn't enabled.
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!isGpsOn) {
            Toast.makeText(context,"Please Turn on Your device Location",
                Toast.LENGTH_LONG).show()
        }


        // Get user current location.
        val cts = CancellationTokenSource()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient
            .lastLocation
//          .getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cts.token)
            .addOnSuccessListener {
                if (it == null){
                    Toast.makeText(context, "Can't get location", Toast.LENGTH_LONG).show()
                } else {
                    latitude = it.latitude
                    longitude = it.longitude
                }
            }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentListVaccineBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Sets the LayoutManager of the recyclerview
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        // Get the input province and input city.
        dropdownProvince = binding.inputProvinsiText
        dropdownCity = binding.inputKotaText


        // Get the list of province.
        var listProvince = ArrayList<Province>()
        RetrofitClient.instance.getListProvince().enqueue(object : Callback<ProvinceResponse> {
            override fun onFailure(call: Call<ProvinceResponse>, t: Throwable) {
                Log.e("Error", "Fetching news failed")
                t.message?.let {
                    Log.e("Error", it)
                    Toast.makeText(context, it,
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse (
                call: Call<ProvinceResponse>, response: Response<ProvinceResponse>
            ) {
                Log.d("INFO", response.code().toString())
                response.body()?.let {
                    val result = response.body()!!.results
                    listProvince.addAll(result)
                }
                var listProvinceString = listOf<String>()
                listProvinceString = listProvince.map {
                    it.key!!
                }

                // Set the adapter of province.
                val provinceAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,
                    listProvinceString)
                dropdownProvince.setAdapter(provinceAdapter)
            }
        })

        // If dropdown province has been clicked then refresh the dropdown city value.
        dropdownProvince.setOnItemClickListener{ parent, view, position, id ->
            dropdownCity.text.clear()
            val selectedProvince = parent.getItemAtPosition(position) as String

            // Get the list of city.
            var listCity = ArrayList<City>()
            RetrofitClient.instance.getListCity(start_id = selectedProvince).enqueue(object : Callback<CityResponse> {
                override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                    Log.e("Error", "Fetching news failed")
                    t.message?.let {
                        Log.e("Error", it)
                        Toast.makeText(context, it,
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
                    Log.d("INFO", response.code().toString())
                    response.body()?.let {
                        val result = response.body()!!.results
                        listCity.addAll(result)
                    }
                    var listCityString = listOf<String>()
                    listCityString = listCity.map {
                        it.key!!
                    }

                    // Set the adapter of province.
                    val cityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,
                        listCityString)
                    dropdownCity.setAdapter(cityAdapter)
                }
            })
        }

        // Set Onclick when searching
        binding.buttonSearch.setOnClickListener{
            var selectedProvince = dropdownProvince.editableText.toString()
            var selectedCity = dropdownCity.editableText.toString()

            if (selectedProvince != "" && selectedCity != ""){

                Log.d("LAT", latitude.toString())
                Log.d("LON", longitude.toString())
                // Get the data from the api.
                var list = ArrayList<Faskes>()
                RetrofitClient.instance.getFaskesVaksinasi(
                    city = selectedCity,
                    province = selectedProvince
                ).enqueue(object : Callback<FaskesResponse>{
                    override fun onFailure(call: Call<FaskesResponse>, t: Throwable) {
                        Log.e("Error", "Fetching news failed")
                        t.message?.let {
                            Log.e("Error", it)
                            Toast.makeText(context, it,
                                Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onResponse(
                        call: Call<FaskesResponse>,
                        response: Response<FaskesResponse>
                    ) {
                        Log.d("INFO", response.code().toString())
                        response.body()?.let {
                            val result = response.body()!!.data
                            print(response.body())
                            list.addAll(result)
                        }
                        Log.d("SIZE", list.size.toString())
                        recyclerView.adapter = ListVaccineAdapter(list)
                    }
                })
            } else {
                Toast.makeText(context, "Select city and province first",
                    Toast.LENGTH_LONG).show()
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        context,
                        "You need the location permission to use this feature",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}