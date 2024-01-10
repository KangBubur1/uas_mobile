package com.example.uas_mobile.Buku

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.Adapter.AdapterBuku
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.R
import com.example.uas_mobile.model.Product
import org.json.JSONObject

class ViewBuku : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    var productList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        displayData()
    }

    private fun displayData() {
        val url= AppConfig().IP_SERVER + "/PHP/viewBuku.php"
        val stringRequest = object : StringRequest(Method.GET,url, Response.Listener { response ->
            productList.clear()
            val jsonObj = JSONObject(response)
            Toast.makeText(this,jsonObj.getString("message"),Toast.LENGTH_SHORT).show()
            val jsonArray = jsonObj.getJSONArray("data")
            var product: Product
            productList.clear()
            for (i in 0..jsonArray.length()-1) {
                val item = jsonArray.getJSONObject(i)
                product = Product()
                product.kodeBuku      = item.getString("kodeBuku")
                product.judulBuku     = item.getString("judulBuku")
                product.pengarang   = item.getString("pengarang")
                product.penerbit   = item.getString("penerbit")
                product.tempatTerbit   = item.getString("tempatTerbit")
                product.jumlahSalinan   = item.getString("jumlahSalinan")
                product.gambarBuku  = AppConfig().IP_SERVER + "/PHP/" + item.getString("gambarBuku")
                product.kodeKategori  = item.getString("kodeKategori")
                productList.add(product)
            }
            recyclerView.adapter = AdapterBuku(this@ViewBuku, productList)
        },
            Response.ErrorListener { error->
                Toast.makeText(this,"Gagal Terhubung",Toast.LENGTH_SHORT).show()
            }
        ){}
        Volley.newRequestQueue(this).add(stringRequest)
    }
}