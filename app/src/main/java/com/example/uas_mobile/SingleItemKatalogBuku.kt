package com.example.uas_mobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.uas_mobile.ViewModel.SingleItemViewModel

class SingleItemKatalogBuku : Fragment() {

    private val viewModel: SingleItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single_item_katalog_buku, container, false)


        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val authorTextView = view.findViewById<TextView>(R.id.authorTextView)
        val categoryTextView = view.findViewById<TextView>(R.id.categoryTextView)
        val imageView = view.findViewById<ImageView>(R.id.gambar)

        viewModel.selectedBookLiveData.observe(viewLifecycleOwner, { book ->
            if (book != null) {
                Log.d("Fragment", "Observed book: $book")
                titleTextView.text = "Title: ${book.judulBuku}"
                authorTextView.text = "Author: ${book.pengarang}"
                categoryTextView.text = "Category: ${book.kategori}"
                Glide.with(this)
                    .load("http://192.168.0.105/PHP/${book.gambarBuku}")
                    .into(imageView)
            }
        })

        return view
    }


}