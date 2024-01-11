package com.example.uas_mobile.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.R
import com.example.uas_mobile.databinding.RvLastSeenBinding


class AdapterLastSeenBuku(private val bookList: List<DataKatalogBuku>): RecyclerView.Adapter<AdapterLastSeenBuku.BookViewHolder>() {

    private val baseUrl = AppConfig().IP_SERVER + "/PHP/"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        val binding = RvLastSeenBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList?.get(position)

        if (book != null) {
            val imageUrl = baseUrl + book.gambarBuku


            if (imageUrl != null) {
                Glide.with(holder.itemView.context)
                    .load(imageUrl)
                    .into(holder.binding.rvImage)
            } else {
                Glide.with(holder.itemView.context)
                    .load(R.drawable.batman)
                    .into(holder.binding.rvImage)
            }
        } else {
            Log.e("AdapterLastSeenBuku","Item at position $position is null")
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class BookViewHolder(val binding: RvLastSeenBinding) : RecyclerView.ViewHolder(binding.root)
}

