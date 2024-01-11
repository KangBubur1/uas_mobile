package com.example.uas_mobile


import ApiService
import android.content.SharedPreferences
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
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.Adapter.AdapterKatalogBuku
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.ViewModel.SingleItemViewModel
import com.example.uas_mobile.model.SaveSelectedBookResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatalogFragment : Fragment(), AdapterKatalogBuku.OnItemClickListener {

    private val bookList = mutableListOf<DataKatalogBuku>()
    private var recyclerView: RecyclerView? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var idMember: String

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

        sharedPreferences = requireContext().getSharedPreferences("loginPref", 0)
        idMember = sharedPreferences.getString("idMember", "") ?: ""
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

    private fun saveSelectedBookToDB(bookCode: String) {
        val url = AppConfig().IP_SERVER + "/PHP/saveSelectedBook.php"

       val stringRequest = object: StringRequest(
           Method.POST,
           url,
           Response.Listener { response ->
               try {
                   val saveResponse = Gson().fromJson(response, SaveSelectedBookResponse::class.java)

                   if (saveResponse.success) {
                       Log.d("CatalogFragment", "Book saved successfully")
                   } else {
                       Log.e("CatalogFragment", "Failed to save book: ${saveResponse.error}")
                   }
               } catch (e: Exception) {
                   e.printStackTrace()
                   Log.e("CatalogFragment", "Error parsing response", e)
               }
           },
           Response.ErrorListener { error ->
               Log.e("CatalogFragment", "Network request failed: ${error.message}")
           }) {
           override fun getParams(): Map<String, String> {
               val params = HashMap<String, String>()
               params["idMember"] = idMember
               params["kodeBuku"] = bookCode
               Log.d("CatalogFragment", "idMember: $idMember")
               return params
           }
       }

        Volley.newRequestQueue(requireContext()).add(stringRequest)
    }



    override fun onItemClick(book: DataKatalogBuku?) {
        if (book != null) {

            saveSelectedBookToDB(book.kodeBuku)
            viewModel.setSelectedBook(book)
            val singleItemFragment = SingleItemKatalogBuku()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, singleItemFragment)
                .addToBackStack(null)
                .commit()
        }

    }

}