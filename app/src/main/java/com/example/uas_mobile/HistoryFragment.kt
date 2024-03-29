package com.example.uas_mobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_mobile.Adapter.AdapterHistory
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HistoryFragment : Fragment() {

    private val bookList = mutableListOf<DataKatalogBuku>()
    private var recyclerView: RecyclerView? = null

    private lateinit var idMember: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerView = view.findViewById(R.id.RV_History)

        var sharedPreferences = requireContext().getSharedPreferences("loginPref", 0)
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
            // Create a URL object with your endpoint
            val url = URL(AppConfig().IP_SERVER + "/PHP/getHistory.php?idMember=$idMember")

            // Open a connection
            val urlConnection = url.openConnection() as HttpURLConnection

            try {
                // Get the InputStream from the connection
                val inputStream = urlConnection.inputStream

                // Read the InputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val responseStringBuilder = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    responseStringBuilder.append(line)
                }

                // Parse the response as needed
                val responseString = responseStringBuilder.toString()
                Log.d("Response", "Raw Response: $responseString")

                val books: List<DataKatalogBuku> = Gson().fromJson(responseString, object : TypeToken<List<DataKatalogBuku>>() {}.type)
                withContext(Dispatchers.Main) {
                    setupRecyclerView(books)
                }

            } finally {
                // Disconnect the HttpURLConnection
                urlConnection.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Error", "Error fetching data: ${e.message}")
        }
    }

    private fun setupRecyclerView(books: List<DataKatalogBuku>) {
        try {
            val adapter = AdapterHistory(books) // Pass the fetched data to the adapter
            recyclerView?.layoutManager = LinearLayoutManager(requireContext())
            recyclerView?.adapter = adapter

            for (book in books) {
                val imageUrl = AppConfig().IP_SERVER + "/PHP/${book.gambarBuku}"
                Log.d("Book", "Judul Buku: ${book.judulBuku}")
                Log.d("Book", "Tanggal Pinjam: ${book.tanggalPinjam}")
                Log.d("Book", "Tanggal Kembali: ${book.tanggalPengembalian}")
                Log.d("Book", "Gambar Buku: $imageUrl")
                Log.d("Book", "Status Buku: ${book.statusPeminjaman}")

            }
        } catch (e: Exception) {
            Log.e("CatalogFragment", "Error setting up RecyclerView adapter", e)
        }
    }



}