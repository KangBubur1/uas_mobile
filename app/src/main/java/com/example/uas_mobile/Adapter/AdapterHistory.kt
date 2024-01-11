package com.example.uas_mobile.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.R
import com.example.uas_mobile.databinding.RvHistoryBinding


class AdapterHistory(private val bookList: List<DataKatalogBuku>): RecyclerView.Adapter<AdapterHistory.BookViewHolder>() {

    private val baseUrl = AppConfig().IP_SERVER + "/PHP/"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHistory.BookViewHolder {
        val binding = RvHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("AdapterHistory", "onCreateViewHolder: ViewHolder created")
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterHistory.BookViewHolder, position: Int) {
        Log.d("AdapterHistory", "onBindViewHolder: Position $position")
        val book = bookList?.get(position)

        val curBook = bookList[position]
        if (book != null) {
            // Use binding to access views
            holder.binding.rvJudul.text = book.judulBuku
            holder.binding.rvTanggalPinjam.text ="Borrowed Date: ${book.tanggalPinjam}"
            holder.binding.rvStatusPeminjaman.text = book.statusPeminjaman


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

            if (curBook.statusPeminjaman.equals("returned", ignoreCase = true)) {
                holder.binding.rvStatusPeminjaman.setBackgroundResource(R.drawable.green_rounded_background)
                holder.binding.rvStatusPeminjaman.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.Primary))
                holder.binding.rvTanggalKembali.text = "Book Return Date: ${curBook.tanggalPengembalian}"
            } else {
                holder.binding.rvStatusPeminjaman.setBackgroundResource(R.drawable.red_rounded_background)
                holder.binding.rvTanggalKembali.text = "Please Return Book At: ${curBook.tanggalPengembalian}"
            }

            Log.d("AdapterKatalogBuku", "Binding item at position $position - ${book.judulBuku}")
        } else {
            Log.e("AdapterKatalogBuku", "Item at position $position is null.")
        }


    }
    override fun getItemCount(): Int {
        return bookList.size
    }
    inner class BookViewHolder(val binding: RvHistoryBinding) : RecyclerView.ViewHolder(binding.root)
}