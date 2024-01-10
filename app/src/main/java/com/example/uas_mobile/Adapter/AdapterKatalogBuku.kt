package com.example.uas_mobile.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.R
import com.example.uas_mobile.databinding.RvItemListBukuBinding

class AdapterKatalogBuku(private val bookList: List<DataKatalogBuku>, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AdapterKatalogBuku.BookViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(book: DataKatalogBuku?)
    }

    private val baseUrl = AppConfig().IP_SERVER + "/PHP/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = RvItemListBukuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("AdapterKatalogBuku", "onCreateViewHolder: ViewHolder created")
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        Log.d("AdapterKatalogBuku", "onBindViewHolder: Position $position")
        val book = bookList?.get(position)

        if (book != null) {
            // Use binding to access views
            holder.binding.rvJudul.text = book.judulBuku
            holder.binding.rvPengarang.text = book.pengarang
            holder.binding.rvKategori.text = book.kategori


            val imageUrl = baseUrl + book.gambarBuku


            // Load image into ImageView using Glide if imageUrl is not null
            if (imageUrl != null) {
                Glide.with(holder.itemView.context)
                    .load(imageUrl)
                    .into(holder.binding.rvImage)
            } else {
                // Handle the case when imageUrl is null (skip loading image or set a placeholder)
                // For example, you can set a placeholder image
                Glide.with(holder.itemView.context)
                    .load(R.drawable.batman)
                    .into(holder.binding.rvImage)
            }

            Log.d("AdapterKatalogBuku", "Binding item at position $position - ${book.judulBuku}")
        } else {
            Log.e("AdapterKatalogBuku", "Item at position $position is null.")
        }

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(book)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class BookViewHolder(val binding: RvItemListBukuBinding) : RecyclerView.ViewHolder(binding.root)
}
