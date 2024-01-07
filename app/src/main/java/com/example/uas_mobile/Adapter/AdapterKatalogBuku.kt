package com.example.uas_mobile.Adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.databinding.RvItemListBinding

class AdapterKatalogBuku(private val bookList: List<DataKatalogBuku>) :
    RecyclerView.Adapter<AdapterKatalogBuku.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = RvItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList?.get(position)

        if (book != null) {
            // Use binding to access views
            holder.binding.rvJudul.text = book.judulBuku
            holder.binding.rvPengarang.text = book.pengarang
            holder.binding.rvKategori.text = book.kategori

            // Mengubah bytearray ke bitmap dan set ke imageView
            if (book.gambarByteArray != null) {
                val bitmap = BitmapFactory.decodeByteArray(book.gambarByteArray, 0, book.gambarByteArray.size)
                holder.binding.rvImage.setImageBitmap(bitmap)
            } else {

            }

            Log.d("AdapterKatalogBuku", "Binding item at position $position - ${book.judulBuku}")
        } else {
            Log.e("AdapterKatalogBuku", "Item at position $position is null.")
        }
    }

    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }

    inner class BookViewHolder(val binding: RvItemListBinding) : RecyclerView.ViewHolder(binding.root)
}
