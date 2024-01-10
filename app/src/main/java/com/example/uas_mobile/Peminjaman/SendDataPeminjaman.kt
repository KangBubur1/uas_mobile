package com.example.uas_mobile.Peminjaman

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class SendDataPeminjaman : AppCompatActivity() {
    private lateinit var etKodePinjam: EditText
    private lateinit var etTanggalPinjam: EditText
    private lateinit var etPeriodePinjam: EditText
    private lateinit var etKodeBuku: EditText
    private lateinit var etIdMember: EditText
    private lateinit var buttonAdd          : Button
    private lateinit var buttonUpdate         : Button
    private lateinit var buttonBack         : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_peminjaman)

        val datePicker: EditText = findViewById(R.id.tanggalPinjamEditText)
        val materialBuilder = MaterialDatePicker.Builder.datePicker()
        val materialDatePicker =
            materialBuilder.build() // Membuat objek MaterialDatePicker dari builder

        materialBuilder.setTitleText("Select Date of Birth")

        materialDatePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selection

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate: String = dateFormat.format(Date(selection))

            datePicker.setText(formattedDate)
        }
        datePicker.setOnClickListener {
            materialDatePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        etKodePinjam = findViewById(R.id.kodePinjamEditText)
        etTanggalPinjam = findViewById(R.id.tanggalPinjamEditText)
        etPeriodePinjam = findViewById(R.id.periodePinjamEditText)
        etKodeBuku = findViewById(R.id.kodeBukuEditText)
        etIdMember = findViewById(R.id.idMemberEditText)
        buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonUpdate = findViewById<Button>(R.id.buttonUpdate)
        buttonBack = findViewById<Button>(R.id.buttonBack)

        buttonAdd.setOnClickListener {
            performAdd()
            val intent = Intent(this@SendDataPeminjaman, ViewDataPeminjaman::class.java)
            startActivity(intent)
            finish()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this@SendDataPeminjaman, ViewDataPeminjaman::class.java)
            startActivity(intent)
            finish()
        }

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
                params["kodePinjam"] = etKodePinjam.text.toString()
                params["tanggalPinjam"] = etTanggalPinjam.text.toString()
                params["periodePinjam"] = etPeriodePinjam.text.toString()
                params["kodeBuku"] = etKodeBuku.text.toString()
                params["idMember"] = etIdMember.text.toString().toInt().toString()
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
            etPeriodePinjam.setText(bundle.getString("periodePinjam"))
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
                            params["periodePinjam"] = etPeriodePinjam.text.toString()
                            params["kodeBuku"]      = etKodeBuku.text.toString()
                            params["idMember"]      = etIdMember.text.toString()
                            return params
                        }
                    }
                    Volley.newRequestQueue(this).add(stringRequest)
                }
            }
        }
    }
