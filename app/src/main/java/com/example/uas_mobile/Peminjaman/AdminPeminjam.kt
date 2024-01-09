package com.example.uas_mobile.Peminjaman

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_mobile.Admin.AdminNav
import com.example.uas_mobile.R

class AdminPeminjam: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_peminjam)
    }

    fun peminjamBack(view: View) {
        val intent = Intent(this, AdminNav::class.java)
        startActivity(intent)
    }
}