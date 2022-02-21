package com.example.perludilindungi.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.model.news.News
import com.squareup.picasso.Picasso


class ListNewsAdapter (private val list: ArrayList<News> = ArrayList())
    :RecyclerView.Adapter<ListNewsAdapter.ListNewsViewHolder>() {

    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class ListNewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        val newsItem = view.findViewById<Button>(R.id.cardList)
        val title: TextView = view.findViewById(R.id.news_title)
        val date: TextView = view.findViewById(R.id.news_date)
        val image: ImageView = view.findViewById(R.id.news_image)
    }

    /**
     * Creates new views with R.layout.list_news_holder as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        //
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_news_holder, parent, false)

        return ListNewsViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        // Override the title.
        holder.title.text = list[position].title

        // Override the date.
        holder.date.text = list[position].pubDate.removeSuffix("+0700")

        // Override image photo.
        if (list[position].enclosure._url != null){
            Picasso.get().load(list[position].enclosure._url)
                .error(R.drawable.ic_android_black_24dp)
                .fit()
                .centerCrop()
                .into(holder.image)
        }

        holder.itemView.setOnClickListener{
            // Create an action from WordList to DetailList
            // using the required arguments
            val action = ListNewsFragmentDirections
                .actionNavigationListNewsToNavigationDetailNews(url = list[position].guid)
            // Navigate using that action
            holder.view.findNavController().navigate(action)
        }
    }

    /**
     * Get the number of item to be showed in recycler view.
     */
    override fun getItemCount(): Int {
        return list.size
    }
}