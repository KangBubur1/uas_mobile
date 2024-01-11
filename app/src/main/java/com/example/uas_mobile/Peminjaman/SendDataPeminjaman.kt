package com.example.uas_mobile.Peminjaman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.Admin.AdminNav
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.Peminjaman.ViewDataPeminjaman
import com.example.uas_mobile.R
import com.google.android.material.datepicker.MaterialDatePicker
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class SendDataPeminjaman : AppCompatActivity() {
    private lateinit var etKodePinjam: EditText
    private lateinit var etTanggalPinjam: EditText
    private lateinit var etTanggalPengembalian: EditText
    private lateinit var etKodeBuku         : EditText
    private lateinit var etIdMember         : EditText
    private lateinit var buttonAdd          : Button
    private lateinit var buttonUpdate        : Button
    private lateinit var btnBack                : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_peminjaman)

        var isDatePinjamFocused = false
        val datePicker: EditText = findViewById(R.id.tanggalPinjamEditText)
        val datePicker2: EditText = findViewById(R.id.tanggalPengembalianEditText)
        val materialBuilder = MaterialDatePicker.Builder.datePicker()
        val materialDatePicker =
            materialBuilder.build() // Membuat objek MaterialDatePicker dari builder

        datePicker
        materialBuilder.setTitleText("Select Date of Birth")

        materialDatePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = SimpleDateFormat("yyyy-MM-dd").format(Date(selection))

            if (isDatePinjamFocused) {
                // Anda ingin memastikan bahwa tanggal yang dipilih di tanggalPinjam
                // selalu sebelum tanggal yang dipilih di tanggalPengembalian.
                if (etTanggalPengembalian.text.isNotEmpty()) {
                    val selectedPengembalian = SimpleDateFormat("yyyy-MM-dd").parse(etTanggalPengembalian.text.toString())
                    val selectedPinjam = SimpleDateFormat("yyyy-MM-dd").parse(selectedDate)

                    if (selectedPinjam >= selectedPengembalian) {
                        Toast.makeText(this, "Tanggal Peminjaman harus sebelum Tanggal Pengembalian", Toast.LENGTH_SHORT).show()
                        return@addOnPositiveButtonClickListener
                    }
                }
                etTanggalPinjam.setText(selectedDate)
            } else {
                // Anda tidak perlu memvalidasi ini karena tanggalPengembalian hanya bisa lebih besar dari tanggalPinjam.
                etTanggalPengembalian.setText(selectedDate)
            }
        }


        datePicker.setOnClickListener {
            isDatePinjamFocused = true
            materialDatePicker.show(supportFragmentManager, "DATE_PICKER")
        }
        datePicker2.setOnClickListener {
            isDatePinjamFocused = false
            materialDatePicker.show(supportFragmentManager, "DATE_PICKER2")
        }

        etKodePinjam = findViewById(R.id.kodePinjamEditText)
        etTanggalPinjam = findViewById(R.id.tanggalPinjamEditText)
        etTanggalPengembalian = findViewById(R.id.tanggalPengembalianEditText)
        etKodeBuku = findViewById(R.id.kodeBukuEditText)
        etIdMember = findViewById(R.id.idMemberEditText)
        buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonUpdate = findViewById<Button>(R.id.buttonUpdate)

        buttonAdd.setOnClickListener {
            performAdd()
            val intent = Intent(this@SendDataPeminjaman, ViewDataPeminjaman::class.java)
            startActivity(intent)
            finish()
        }

//        buttonBack.setOnClickListener {
//            val intent = Intent(this@SendDataPeminjaman, ViewDataPeminjaman::class.java)
//            startActivity(intent)
//            finish()
//        }

        updateData()

    }
    private fun performAdd(){
        val url = AppConfig().IP_SERVER + "/PHP/inputPeminjaman.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Log.d("Response", "Server Response: $response")
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["kodePinjam"]            = etKodePinjam.text.toString()
                params["tanggalPinjam"]         = etTanggalPinjam.text.toString()
                params["tanggalPengembalian"]   = etTanggalPengembalian.text.toString()
                params["kodeBuku"]              = etKodeBuku.text.toString()
                params["idMember"]              = etIdMember.text.toString().toInt().toString()
                return params
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)

    }
    private fun updateData() {
        val bundle = intent.getBundleExtra("dataPeminjaman")
        if (bundle != null) {
            etKodePinjam.setText(bundle.getString("kodePinjam"))
            etTanggalPinjam.setText(bundle.getString("tanggalPinjam"))
            etTanggalPengembalian.setText(bundle.getString("tanggalPengembalian"))
            etKodeBuku.setText(bundle.getString("kodeBuku"))
            etIdMember.setText(bundle.getString("idMember"))

            //visible edit button and hide save button
            buttonAdd.visibility        = View.GONE
            buttonUpdate.visibility     = View.VISIBLE

            buttonUpdate.setOnClickListener{
                    val url1: String =AppConfig().IP_SERVER + "/PHP/updatePeminjaman.php"
                    val stringRequest = object : StringRequest(Method.POST, url1,
                        Response.Listener { response ->
                            val jsonObj = JSONObject(response)
                            Toast.makeText(this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SendDataPeminjaman, ViewDataPeminjaman::class.java)
                            startActivity(intent)
                            finish()
                        },
                        Response.ErrorListener { _ ->
                            Toast.makeText(this, "Gagal Terhubung", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        override fun getParams(): HashMap<String, String> {
                            val params = HashMap<String, String>()
                            params["kodePinjam"]    = etKodePinjam.text.toString()
                            params["tanggalPinjam"] = etTanggalPinjam.text.toString()
                            params["tanggalPengembalian"] = etTanggalPengembalian.text.toString()
                            params["kodeBuku"]      = etKodeBuku.text.toString()
                            params["idMember"]      = etIdMember.text.toString()
                            return params
                        }
                    }
                    Volley.newRequestQueue(this).add(stringRequest)
                }
            }
        }
    fun back(){
        val intent = Intent(this, ViewDataPeminjaman::class.java)
        startActivity(intent)
        finish()
        }
    fun backmenu(){
        val intent = Intent(this, AdminNav::class.java)
        startActivity(intent)
        finish()
    }
    }
