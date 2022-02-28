package com.example.perludilindungi.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.model.faskes.Faskes



class ListVaccineAdapter (private val list: ArrayList<Faskes> = ArrayList())
    :RecyclerView.Adapter<ListVaccineAdapter.ListVaccineViewHolder>() {

    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class ListVaccineViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.faskesName)
        val address: TextView = view.findViewById(R.id.faskesAddress)
        val type: TextView = view.findViewById(R.id.faskesType)
        val telp: TextView = view.findViewById(R.id.faskesTelp)
        val code: TextView = view.findViewById(R.id.faskesCode)
    }

    /**
     * Creates new views with R.layout.list_vaccines_holder as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVaccineViewHolder {
        //
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_vaccines_holder, parent, false)

        return ListVaccineViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: ListVaccineViewHolder, position: Int) {
        // Override the attribute
        holder.address.text = list[position].alamat
        holder.code.text = holder.itemView.context.getString(
            R.string.kodeFaskes, list[position].kode)
        holder.name.text = list[position].nama
        var faskesTelp = list[position].telp
        if (faskesTelp == "" || faskesTelp == null){
            faskesTelp = "Telp tidak tersedia"
        }
        holder.telp.text = faskesTelp

        var jenisFaskes = list[position].jenis_faskes
        // Override jenis faskes.
        when (jenisFaskes){
            "" -> {
                jenisFaskes = "Lainnya"
                holder.type.setBackgroundResource(R.color.pastel_red)
            }
            "PUSKESMAS" -> {
                holder.type.setBackgroundResource(R.color.pink)
            }
            "RUMAH SAKIT" -> {
                holder.type.setBackgroundResource(R.color.pastel_blue)
            }
            "KLINIK" -> {
                holder.type.setBackgroundResource(R.color.purple_500)
            }
        }
        holder.type.text = jenisFaskes

        holder.itemView.setOnClickListener{
            // TODO: Kinon implement this
        }
    }

    /**
     * Get the number of item to be showed in recycler view.
     */
    override fun getItemCount(): Int {
        return list.size
    }
}