package com.example.perludilindungi.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.database.FaskesDao
import com.example.perludilindungi.database.FaskesDatabase
import com.example.perludilindungi.database.FaskesEntity
import com.example.perludilindungi.databinding.FragmentBookmarkBinding
import com.example.perludilindungi.model.faskes.Faskes
import com.example.perludilindungi.model.news.News
import com.example.perludilindungi.ui.news.ListVaccineAdapter
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    var list = ArrayList<Faskes>()

    private lateinit var model: BookmarkViewModel
    private lateinit var adapter: ListVaccineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
        val notificationsViewModel =
            ViewModelProvider(this).get(BookmarkViewModel::class.java
        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        */

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*
        // Create an instance of the ViewModel Factory.
        val application = requireNotNull(this.activity).application
        val dataSource = FaskesDatabase.getInstance(application).faskesDao
        val viewModelFactory = BookmarkViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val bookmarkViewModel = ViewModelProvider(this, viewModelFactory).get(BookmarkViewModel::class.java)
         */

        model = ViewModelProvider(this).get(BookmarkViewModel::class.java)
        adapter = ListVaccineAdapter(list, true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        recyclerView = binding.listBookmarkedFaskes
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        model.getListFaskes().observe(viewLifecycleOwner, object : Observer<List<FaskesEntity>>{
            override fun onChanged(toList: List<FaskesEntity>?) {
                var toSet = ArrayList<Faskes>()
                for (i in toList?.indices!!) {
                    val toAdd = Faskes(
                        toList[i].id, toList[i].kode, toList[i].nama, toList[i].kota,
                        toList[i].provinsi, toList[i].alamat, toList[i].latitude, toList[i].longitude,
                        toList[i].telp, toList[i].jenis_faskes, "", toList[i].status,
                        source_data = ""
                    )
                    toSet.add(toAdd)
                }
                Log.d("INFO", toSet.toString())
                adapter.setList(toSet)
            }

        })

        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}