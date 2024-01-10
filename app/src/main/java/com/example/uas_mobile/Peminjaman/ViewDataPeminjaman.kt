package com.example.uas_mobile.Peminjaman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.Adapter.AdapterPeminjaman
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.R
import com.example.uas_mobile.model.Peminjaman
import org.json.JSONObject

class ViewDataPeminjaman : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    var pinjamList = ArrayList<Peminjaman>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_data_peminjaman)
        recyclerView = findViewById(R.id.rvPeminjaman)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        displayData()

    }
    private fun displayData() {
        val url: String = AppConfig().IP_SERVER + "/PHP/viewDataPeminjaman.php"
        val stringRequest = object : StringRequest(Method.GET,url, Response.Listener { response ->
            pinjamList.clear()
            val jsonObj = JSONObject(response)
            Toast.makeText(this,jsonObj.getString("message"),Toast.LENGTH_SHORT).show()
            val jsonArray = jsonObj.getJSONArray("data")
            var peminjaman: Peminjaman
            pinjamList.clear()
            for (i in 0..jsonArray.length()-1) {
                val item = jsonArray.getJSONObject(i)
                peminjaman = Peminjaman()
                peminjaman.kodePinjam     = item.getString("kodePinjam")
                peminjaman.tanggalPinjam  = item.getString("tanggalPinjam")
                peminjaman.periodePinjam  = item.getString("periodePinjam")
                peminjaman.kodeBuku      = item.getString("kodeBuku")
                peminjaman.idMember      = item.getString("idMember")
                pinjamList.add(peminjaman)
            }
            recyclerView.adapter = AdapterPeminjaman(this@ViewDataPeminjaman, pinjamList)
        },
            Response.ErrorListener { error->
                Toast.makeText(this,"Gagal Terhubung",Toast.LENGTH_SHORT).show()
            }
        ){}
        Volley.newRequestQueue(this).add(stringRequest)
    }
}