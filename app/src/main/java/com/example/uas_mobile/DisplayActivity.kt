package com.example.uas_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class DisplayActivity : AppCompatActivity() {

    private lateinit var listview : ListView
    var arrayList_data = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        listview = findViewById(R.id.listViewBuku)
        getData()

        listview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(
                this@DisplayActivity,
                SendData::class.java
            )
            intent.putExtra("kodeBuku", arrayList_data[position].kodeBuku)
            intent.putExtra("judulBuku", arrayList_data[position].judulBuku)
            intent.putExtra("kodeKategori", arrayList_data[position].kodeKategori)
            intent.putExtra("gambarBuku", arrayList_data[position].gambarBuku)
            intent.putExtra("pengarang", arrayList_data[position].pengarang)
            intent.putExtra("penerbit", arrayList_data[position].penerbit)
            intent.putExtra("tempatTerbit", arrayList_data[position].tempatTerbit)
            intent.putExtra("jumlahSalinan", arrayList_data[position].jumlahSalinan)
            startActivity(intent)
        }

    }
    private fun getData(){
        val url: String = "http://192.168.100.33/PHP/viewBuku.php"
        val stringRequest = object : StringRequest(
            Method.GET,url,
            Response.Listener { response ->
                Log.d("Response", "Server Response: $response")
                try {
                    val jsonObj = JSONObject(response)
                    Toast.makeText(this,jsonObj.getString("error_text"), Toast.LENGTH_SHORT).show()
                    val jsonArray = jsonObj.getJSONArray("data")
                    var product: Product
                    arrayList_data.clear()
                    for (i in 0..jsonArray.length()-1) {


                        val item = jsonArray.getJSONObject(i)
                        product = Product()
                        product.kodeBuku = "kode-buku : ${item.getString("kodeBuku")}"
                        product.judulBuku = "judul-buku : ${item.getString("judulBuku")}"
                        product.kodeKategori = "kode-kategori : ${item.getString("kodeKategori")}"
                        product.pengarang = "pengarang : ${item.getString("pengarang")}"
                        product.penerbit = "penerbit : ${item.getString("penerbit")}"
                        product.tempatTerbit = "tempat-terbit : ${item.getString("tempatTerbit")}"
                        product.jumlahSalinan = "jumlah-salinan : ${item.getString("jumlahSalinan")}"
                        product.gambarBuku =
                            "http://192.168.100.33/PHP/" + item.getString("gambarBuku")

                        arrayList_data.add(product)

                    }
                }catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Invalid response from server", Toast.LENGTH_SHORT).show()
                }
                listview.adapter = MyAdapter(this@DisplayActivity, arrayList_data)
            },
            Response.ErrorListener { _ ->
                Toast.makeText(this,"Gagal Terhubung", Toast.LENGTH_SHORT).show()
            }
        ){}
        Volley.newRequestQueue(this).add(stringRequest)
    }
}