package com.example.uas_mobile


import ApiService
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_mobile.Adapter.AdapterKatalogBuku
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.ViewModel.SingleItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatalogFragment : Fragment(), AdapterKatalogBuku.OnItemClickListener {

    private val bookList = mutableListOf<DataKatalogBuku>()
    private var recyclerView: RecyclerView? = null

    private val apiService = Retrofit.Builder()
        .baseUrl(AppConfig().IP_SERVER + "/PHP/getCategory.php/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val viewModel: SingleItemViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catalog, container, false)
        recyclerView = view.findViewById(R.id.RV_Katalog)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            fetchDataFromDatabase()
        }

    }

    private suspend fun fetchDataFromDatabase() {
        try {
            // Fetch all books from the API
            val books = apiService.getBooks()

            Log.d("Book", "Raw JSON Response: $books")

            withContext(Dispatchers.Main) {
                // Update your RecyclerView or perform any other actions with the fetched books
                setupRecyclerView(books)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupRecyclerView(books: List<DataKatalogBuku>) {
        bookList.addAll(books)
        recyclerView?.let {
            try {
                val adapter = AdapterKatalogBuku(bookList, this@CatalogFragment)
                it.layoutManager = LinearLayoutManager(requireContext())
                it.adapter = adapter
            }catch (e: Exception) {
                Log.e("CatalogFragment", "Error setting up RecyclerView adapter", e)
            }

        }

        for (book in bookList) {
            val imageUrl = AppConfig().IP_SERVER + "/PHP/${book.gambarBuku}"  // Sesuaikan dengan nama field gambarByteArray
            Log.d("Book", "Judul Buku: ${book.judulBuku}")
            Log.d("Book", "Pengarang: ${book.pengarang}")
            Log.d("Book", "Kategori: ${book.kategori}")
            Log.d("Book", "Gambar Buku: $imageUrl")
        }
    }

    override fun onItemClick(book: DataKatalogBuku?) {
        if (book != null) {
            viewModel.setSelectedBook(book)
            val singleItemFragment = SingleItemKatalogBuku()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, singleItemFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}