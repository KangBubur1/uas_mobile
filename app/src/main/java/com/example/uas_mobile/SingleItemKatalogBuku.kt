package com.example.uas_mobile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.health.connect.datatypes.units.Length
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.ViewModel.SingleItemViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class SingleItemKatalogBuku : Fragment() {

    private val viewModel: SingleItemViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single_item_katalog_buku, container, false)

        // Ambil Id Member
        val sharedPreferences = requireContext().getSharedPreferences("loginPref",0)

        val idMember = sharedPreferences.getString("idMember", "") ?: ""

        Log.d("idMember","$idMember")



        // Ambil Data Buku
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val authorTextView = view.findViewById<TextView>(R.id.authorTextView)
        val categoryTextView = view.findViewById<TextView>(R.id.categoryTextView)
        val imageView = view.findViewById<ImageView>(R.id.gambar)
        val btnBooking = view.findViewById<MaterialButton>(R.id.btnBooking)

        viewModel.selectedBookLiveData.observe(viewLifecycleOwner) { book ->
            if (book != null) {
                Log.d("Fragment", "Observed book: $book")
                titleTextView.text = "Title: ${book.judulBuku}"
                authorTextView.text = "Author: ${book.pengarang}"
                categoryTextView.text = "Category: ${book.kategori}"
                Glide.with(this)
                    .load(AppConfig().IP_SERVER + "/PHP/${book.gambarBuku}")
                    .into(imageView)

                btnBooking.setOnClickListener{
                    Log.d("kodeBuku","${book.kodeBuku}")
                    MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
                        .setTitle("Book ${book.judulBuku}")
                        .setMessage("Are You Sure To Book This Book?")
                        .setNegativeButton("Decline") {dialog, which ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Accept") {dialog, which ->
                            GlobalScope.launch(Dispatchers.Main){
                                val url = AppConfig().IP_SERVER + "/PHP/cekStokBuku.php"
                                val requestBody = FormBody.Builder()
                                    .add("kodeBuku", book.kodeBuku)
                                    .build()

                                val request = Request.Builder()
                                    .url(url)
                                    .post(requestBody)
                                    .build()

                                withContext(Dispatchers.IO) {
                                    try {
                                        val response = OkHttpClient().newCall(request).execute()
                                        val responseData = response.body?.string()

                                        // Handle the response here
                                        if (response.isSuccessful) {
                                            val jsonResponse = JSONObject(responseData)
                                            val status = jsonResponse.getString("status")

                                            if (status == "success") {
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(requireContext(), "Booking Successful!", Toast.LENGTH_SHORT).show()

                                                }

                                                // Insert ke Tabel Peminjam
                                                val kodePinjam = generateKodePinjam()

                                                val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd").format(java.util.Date())

                                                val returnDate = java.text.SimpleDateFormat("yyyy-MM-dd")
                                                    .format(java.util.Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)))

                                                val insertBookingUrl = AppConfig().IP_SERVER + "/PHP/userInputPeminjaman.php"
                                                val insertBookRequestForm = FormBody.Builder()
                                                    .add("kodePinjam", kodePinjam)
                                                    .add("tanggalPinjam",currentDate)
                                                    .add("tanggalPengembalian", returnDate)
                                                    .add("kodeBuku",book.kodeBuku)
                                                    .add("idMember",idMember)
                                                    .build()

                                                val insertBookRequest = Request.Builder()
                                                    .url(insertBookingUrl)
                                                    .post(insertBookRequestForm)
                                                    .build()

                                                try {
                                                    val insertBookingResponse =
                                                        OkHttpClient().newCall(insertBookRequest).execute()

                                                    if (insertBookingResponse.isSuccessful) {
                                                        val insertBookingResponseData = insertBookingResponse.body?.string()
                                                        Log.d("InsertBooking", "Response data: $insertBookingResponseData")
                                                    } else {
                                                        Log.e("InsertBooking", "Insert booking request unsuccessful")
                                                    }
                                                } catch (e: IOException) {
                                                    e.printStackTrace()
                                                    Log.e("InsertBooking", "Insert booking request failed due to exception")
                                                }

                                                // Update Buku
                                                val updateUrl = AppConfig().IP_SERVER + "/PHP/updateStokBuku.php"
                                                val updateRequestBody = FormBody.Builder()
                                                    .add("kodeBuku", book.kodeBuku)
                                                    .build()

                                                val updateRequest = Request.Builder()
                                                    .url(updateUrl)
                                                    .post(updateRequestBody)
                                                    .build()

                                                withContext(Dispatchers.IO) {
                                                    try {
                                                        val updateResponse = OkHttpClient().newCall(updateRequest).execute()

                                                        // Handle the response of the update request if needed
                                                        if (updateResponse.isSuccessful) {
                                                            val updateResponseData = updateResponse.body?.string()
                                                            Log.d("UpdateBuku", "Update response data: $updateResponseData")

                                                            try {
                                                                // Attempt to parse the response as JSON
                                                                val updateJsonResponse = JSONObject(updateResponseData)
                                                                val updateStatus = updateJsonResponse.getString("status")

                                                                if (updateStatus == "success") {
                                                                    // The book stock has been successfully updated
                                                                    Log.d("UpdateBuku", "Book stock updated successfully")
                                                                } else {
                                                                    // Handle the case where the stock update was not successful
                                                                    Log.e("UpdateBuku", "Book stock update failed")
                                                                }
                                                            } catch (e: JSONException) {
                                                                // Handle the case where the response is not a valid JSON
                                                                Log.e("UpdateBuku", "Invalid JSON response")
                                                            }
                                                        } else {
                                                            // Handle unsuccessful response of the update request
                                                            Log.e("UpdateBuku", "Update request unsuccessful")
                                                        }
                                                    } catch (e: IOException) {
                                                        e.printStackTrace()
                                                        // Handle network or IO exception for the update request
                                                        Log.e("UpdateBuku", "Update request failed due to exception")
                                                    }
                                                }
                                            } else {
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(requireContext(), "Book not available", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }

                                    } catch (e: IOException) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }
                        .show()

                }
            }
        }

        return view
    }


    private fun generateKodePinjam(): String {
        return  "P${System.currentTimeMillis()}"
    }

    private fun showNotification(title: String, content: String) {
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification Channel is required for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default",
                "Booking Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Create a notification
        val builder = NotificationCompat.Builder(requireContext(), "default")

            .setContentTitle(title)
            .setContentText(content)

            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Show the notification
        notificationManager.notify(1, builder.build())
    }
}