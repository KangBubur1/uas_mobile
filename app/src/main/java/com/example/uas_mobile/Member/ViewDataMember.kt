package com.example.uas_mobile.Member

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.Adapter.AdapterMember
import com.example.uas_mobile.Adapter.AdapterPeminjaman
import com.example.uas_mobile.Admin.AdminNav
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.Peminjaman.SendDataPeminjaman
import com.example.uas_mobile.R
import com.example.uas_mobile.model.Member
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject

class ViewDataMember : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    var memberList = ArrayList<Member>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_data_member)
        recyclerView = findViewById(R.id.rvMember)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        displayData()

    }
    private fun displayData() {
        val url: String = AppConfig().IP_SERVER + "/PHP/viewMember.php"
        val stringRequest = object : StringRequest(Method.POST,url, Response.Listener { response ->
            memberList.clear()
            val jsonObj = JSONObject(response)
            val jsonArray = jsonObj.getJSONArray("data")
            var member: Member
            memberList.clear()
            for (i in 0..jsonArray.length()-1) {
                val item = jsonArray.getJSONObject(i)
                member = Member()
                member.idMember         = item.getString("idMember")
                member.username         = item.getString("username")
                member.name             = item.getString("name")
                member.password         = item.getString("password")
                member.tempatLahir      = item.getString("tempatLahir")
                member.tanggalLahir     = item.getString("tanggalLahir")
                member.noTelepon        = item.getString("noTelepon")
                member.status           = item.getString("status")
                memberList.add(member)
            }
            recyclerView.adapter = AdapterMember(this@ViewDataMember, memberList)
        },
            Response.ErrorListener { error->
                Toast.makeText(this,"Gagal Terhubung",Toast.LENGTH_SHORT).show()
            }
        ){}
        Volley.newRequestQueue(this).add(stringRequest)
    }
    fun peminjamBack(view: View) {
        val intent = Intent(this, AdminNav::class.java)
        startActivity(intent)
        finish()
    }

}