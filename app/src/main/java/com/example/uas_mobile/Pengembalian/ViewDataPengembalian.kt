package com.example.uas_mobile.Pengembalian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.Adapter.AdapterPengembalian
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.R
import com.example.uas_mobile.model.Pengembalian
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject

class ViewDataPengembalian : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    var kembaliList = ArrayList<Pengembalian>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_data_pengembalian)
        recyclerView = findViewById(R.id.rvPengembalian)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        displayData()


        val fabAdd: FloatingActionButton = findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener {
            val intent = Intent(this, SendDataPengembalian::class.java)
            startActivity(intent)
        }

    }
    private fun displayData() {
        val url: String = AppConfig().IP_SERVER + "/PHP/viewDataPengembalian.php"
        val stringRequest = object : StringRequest(Method.GET,url, Response.Listener { response ->
            Log.d("Response", "Server Response: $response")
            kembaliList.clear()
            val jsonObj = JSONObject(response)
            Toast.makeText(this,jsonObj.getString("message"),Toast.LENGTH_SHORT).show()
            val jsonArray = jsonObj.getJSONArray("data")
            var pengembalian: Pengembalian
            kembaliList.clear()
            for (i in 0..jsonArray.length()-1) {
                val item = jsonArray.getJSONObject(i)
                pengembalian = Pengembalian()
                pengembalian.kodeKembali    = item.getString("kodeKembali")
                pengembalian.tanggalKembali  = item.getString("tanggalKembali")
                pengembalian.kodePinjam  = item.getString("kodePinjam")
                kembaliList.add(pengembalian)
            }
            recyclerView.adapter = AdapterPengembalian(this@ViewDataPengembalian, kembaliList)
        },
            Response.ErrorListener { error->
                Toast.makeText(this,"Gagal Terhubung",Toast.LENGTH_SHORT).show()
            }
        ){}
        Volley.newRequestQueue(this).add(stringRequest)
    }

}