package com.example.uas_mobile.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.uas_mobile.Buku.ViewBuku
import com.example.uas_mobile.MainActivity
import com.example.uas_mobile.Member.ViewDataMember
import com.example.uas_mobile.Peminjaman.ViewDataPeminjaman
import com.example.uas_mobile.Pengembalian.ViewDataPengembalian
import com.example.uas_mobile.R

class AdminNav : AppCompatActivity() {
    private lateinit var btnLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_nav)

        btnLogout = findViewById(R.id.btnlogoutadmin)

        btnLogout.setOnClickListener {
            logoutUser()
        }
    }


    fun dataBuku (view: View) {
        val intent = Intent(this, ViewBuku::class.java)
        startActivity(intent)

    }

    fun peminjamanBuku (view: View) {
        val intent = Intent(this, ViewDataPeminjaman::class.java)
        startActivity(intent)

    }
    fun pengembalianBuku (view: View) {
        val intent = Intent(this, ViewDataPengembalian::class.java)
        startActivity(intent)

    }

    fun member (view: View) {
        val intent = Intent(this, ViewDataMember::class.java)
        startActivity(intent)

    }
    private fun logoutUser() {
        val sharedPreferences = getSharedPreferences("loginPref", 0)
        val editor = sharedPreferences.edit()

        // Hapus semua data yang disimpan di SharedPreferences
        editor.clear()
        editor.apply()

        // Navigasi kembali ke layar login atau halaman utama
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
