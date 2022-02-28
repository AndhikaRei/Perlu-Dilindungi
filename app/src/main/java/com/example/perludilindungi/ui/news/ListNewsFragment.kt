package com.example.perludilindungi.ui.news

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.databinding.FragmentListNewsBinding
import com.example.perludilindungi.model.news.News
import com.example.perludilindungi.model.news.NewsResponse
import com.example.perludilindungi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNewsFragment : Fragment() {

    private var _binding: FragmentListNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // The recycler view and the layout manager of the recycler view.
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentListNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Sets the LayoutManager of the recyclerview
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        // Get the data from the api.
        var list = ArrayList<News>()
        RetrofitClient.instance.getListNews().enqueue(object : Callback<NewsResponse>{
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("Error", "Fetching news failed")
                t.message?.let {
                    Log.e("Error", it)
                    Toast.makeText(context, it,
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                Log.d("INFO", response.code().toString())
                response.body()?.let {
                    val result = response.body()!!.result
                    list.addAll(result)
                }
                recyclerView.adapter = ListNewsAdapter(list)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


