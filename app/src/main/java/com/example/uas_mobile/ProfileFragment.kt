package com.example.uas_mobile

import android.content.Intent
import android.graphics.Outline
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_mobile.Adapter.AdapterLastSeenBuku
import com.example.uas_mobile.DataBuku.DataKatalogBuku
import com.example.uas_mobile.User.UserEditProfile
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ProfileFragment : Fragment() {

    private val bookList = mutableListOf<DataKatalogBuku>()
    private var recyclerView: RecyclerView? = null

    private lateinit var idMember: String
    private lateinit var tvName: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerView = view.findViewById(R.id.rv_Profil)

        // Ambil idMember
        var sharedPreferences = requireContext().getSharedPreferences("loginPref", 0)
        idMember = sharedPreferences.getString("idMember", "") ?: ""

        // Logout
        val logout: MaterialButton = view.findViewById(R.id.btnLogout)
        logout.setOnClickListener {
            logout()
        }

        val editProfile: AppCompatButton = view.findViewById(R.id.btnEditProfile)
        editProfile.setOnClickListener{
            editProfile()
        }

        //Name
        tvName = view.findViewById(R.id.txtname)

        updateUIPreferences()

        // shadow
        val secondRowProfile = view.findViewById<LinearLayout>(R.id.secondRowProfile)

        ViewCompat.setBackground(secondRowProfile, requireContext().getDrawable(R.drawable.background_cardview))
        ViewCompat.setElevation(secondRowProfile, 8f) // Sesuaikan nilai elevasi sesuai kebutuhan
        ViewCompat.setTranslationZ(secondRowProfile, 8f) // Sesuaikan nilai translasi Z sesuai kebutuhan
        secondRowProfile.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, (view.height + 8).toInt(), 16f)
            }
        }
        secondRowProfile.clipToOutline = true

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
            val url = URL(AppConfig().IP_SERVER + "/PHP/getSelectedBook.php?idMember=$idMember" )

            val urlConnection = url.openConnection() as HttpURLConnection

            try {
                val inputStream = urlConnection.inputStream

                val reader = BufferedReader(InputStreamReader(inputStream))
                val responseStringBuilder = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null){
                    responseStringBuilder.append(line)
                }

                val responseString = responseStringBuilder.toString()
                Log.d("Response", "Raw Response: $responseString")
                val books: List<DataKatalogBuku> = Gson().fromJson(responseString, object : TypeToken<List<DataKatalogBuku>>() {}.type)
                withContext(Dispatchers.Main){
                    setupRecyclerView(books)
                }
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Error", "Error fetching data: ${e.message}")
        }
    }

    private fun setupRecyclerView(books: List<DataKatalogBuku>) {
        bookList.addAll(books)
        recyclerView?.let {
            try {
                val adapter = AdapterLastSeenBuku(bookList)
                it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                it.adapter = adapter
            } catch (e: Exception) {
                Log.e("ProfileFragment", "Error setting up RecyclerView adapter", e)
            }
        }
    }

    private fun updateUIPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("loginPref",0)
        val name = sharedPreferences.getString("name", "") ?: ""
        tvName.text = name
    }


    private fun logout() {

        Log.d("ProfileFragment", "Logout button clicked")
        val sharedPreferences = requireContext().getSharedPreferences("loginPref",0)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun editProfile() {
        val intent = Intent(requireContext(), UserEditProfile::class.java)
        startActivity(intent)
    }

}