package com.example.uas_mobile.Buku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.uas_mobile.Admin.AdminNav
import com.example.uas_mobile.R

class AdminBuku : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_buku)
    }

    fun bukuBack(view: View) {
        val intent = Intent(this, AdminNav::class.java)
        startActivity(intent)
    }
}