package com.example.uas_mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_mobile.databinding.CarouselItemBinding

class BookAdapter(private var bookList: List<BookModel>): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    class BookViewHolder(val binding: CarouselItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.binding.apply {
            Glide.with(bookImage).load(book.image).into(bookImage)
            bookName.text= book.name

            bookImage.setOnClickListener{
                Toast.makeText(it.context, book.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = bookList.size
}