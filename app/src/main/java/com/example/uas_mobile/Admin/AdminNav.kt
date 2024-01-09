package com.example.uas_mobile.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.uas_mobile.Buku.AdminBuku
import com.example.uas_mobile.Peminjaman.AdminPeminjam
import com.example.uas_mobile.R

class AdminNav : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_nav)
    }

    fun dataBuku (view: View) {
        val intent = Intent(this, AdminBuku::class.java)
        startActivity(intent)

    }

    fun peminjamanBuku (view: View) {
        val intent = Intent(this, AdminPeminjam::class.java)
        startActivity(intent)

    }
}