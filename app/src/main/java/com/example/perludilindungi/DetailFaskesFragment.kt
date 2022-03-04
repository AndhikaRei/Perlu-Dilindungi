package com.example.perludilindungi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.perludilindungi.database.FaskesDao
import com.example.perludilindungi.database.FaskesDatabase
import com.example.perludilindungi.database.FaskesEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ID = "id"
private const val NAMA = "nama"
private const val KODE = "kode"
private const val JENIS_FASKES = "type"
private const val ALAMAT = "alamat"
private const val TELP = "telp"
private const val STATUS = "status"
private const val LATITUDE = "latitude"
private const val LONGITUDE = "longitude"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFaskesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFaskesFragment : Fragment() {

    private var iD: Int = 0
    private lateinit var nama: String
    private lateinit var kode: String
    private lateinit var jenis_faskes: String
    private lateinit var alamat: String
    private lateinit var telp: String
    private lateinit var status: String
    private lateinit var latitude: String
    private lateinit var longitude: String

    private lateinit var faskesDao: FaskesDao
    private var bookmarked: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            iD = it.getInt(ID).toInt()
            nama = it.getString(NAMA).toString()
            kode = it.getString(KODE).toString()
            jenis_faskes = it.getString(JENIS_FASKES).toString()
            alamat = it.getString(ALAMAT).toString()
            telp = it.getString(TELP).toString()
            if(telp == "" || telp == null){
                telp = "Telp tidak tersedia"
            }
            status = it.getString(STATUS).toString()
            latitude = it.getString(LATITUDE).toString()
            longitude = it.getString(LONGITUDE).toString()
        }
        faskesDao = FaskesDatabase.getInstance(requireContext()).faskesDao
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_faskes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nameTextView: TextView = view.findViewById(R.id.faskesName2)
        nameTextView.text = "FASKES " + nama
        val codeTextView: TextView = view.findViewById(R.id.faskesCode)
        codeTextView.text = "Kode: " + kode
        val typeTextView: TextView = view.findViewById(R.id.faskesType)
        when (jenis_faskes){
            "" -> {
                jenis_faskes = "Lainnya"
                typeTextView.setBackgroundResource(R.color.pastel_red)
            }
            "PUSKESMAS" -> {
                typeTextView.setBackgroundResource(R.color.pink)
            }
            "RUMAH SAKIT" -> {
                typeTextView.setBackgroundResource(R.color.pastel_blue)
            }
            "KLINIK" -> {
                typeTextView.setBackgroundResource(R.color.purple_500)
            }
        }
        typeTextView.text = jenis_faskes
        val addressTextView: TextView = view.findViewById(R.id.faskesAddress)
        addressTextView.text = alamat
        val telpTextView: TextView = view.findViewById(R.id.faskesTelp)
        telpTextView.text = "Telp: " + telp

        val gmapsButton: Button = view.findViewById(R.id.gmapButton)
        gmapsButton.setOnClickListener {
            // Creates an Intent that will load a map of San Francisco
            val gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        val bookmarkButton : Button = view.findViewById(R.id.bookmarkButton)
        //bookmarkButton.isEnabled = false
        refreshButton(bookmarkButton)

        bookmarkButton.setOnClickListener {
            Log.d("INFO", "Bookmark dipencet")
            GlobalScope.launch {
                if(bookmarked){
                    faskesDao.delete(iD)
                    Log.d("INFO", "Sukses menghapus faskes dengan id" + iD.toString())
                }else{
                    val toInsert =
                        FaskesEntity(iD, kode, nama,
                        "", "", alamat,
                        latitude, longitude, telp,
                        jenis_faskes, status)
                    faskesDao.insert(toInsert)
                    Log.d("INFO", "Sukses menambahkan faskes dengan id" + iD.toString())
                }
                refreshButton(bookmarkButton)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun refreshButton(bookmarkButton: Button){
        GlobalScope.launch {
            if(faskesDao.isBookmarked(iD) > 0){
                bookmarkButton.text = "- Bookmark"
                bookmarked = true
                //bookmarkButton.isEnabled = true
            }else{
                bookmarkButton.text = "+ Bookmark"
                bookmarked = false
                //bookmarkButton.isEnabled = true
            }
        }
    }

}