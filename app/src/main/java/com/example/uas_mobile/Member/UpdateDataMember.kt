package com.example.uas_mobile.Member

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
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.Peminjaman.ViewDataPeminjaman
import com.example.uas_mobile.R
import com.google.android.material.datepicker.MaterialDatePicker
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UpdateDataMember : AppCompatActivity() {
    private lateinit var idMemberUpdateEditText : EditText
    private lateinit var usernameEditText       : EditText
    private lateinit var nameEditText           : EditText
    private lateinit var tempatLahirEditText    : EditText
    private lateinit var tanggalLahirEditText   : EditText
    private lateinit var noTeleponEditText      : EditText
    private lateinit var buttonUpdate           : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_member)

        val datePicker: EditText = findViewById(R.id.tanggalLahirEditText)
        val materialBuilder = MaterialDatePicker.Builder.datePicker()
        val materialDatePicker =
            materialBuilder.build() // Membuat objek Material DatePicker dari builder

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

        idMemberUpdateEditText = findViewById(R.id.idMemberUpdateEdiText)
        usernameEditText = findViewById(R.id.usernameEditText)
        nameEditText = findViewById(R.id.nameEditText)
        tempatLahirEditText = findViewById(R.id.tempatLahirEditText)
        tanggalLahirEditText = findViewById(R.id.tanggalLahirEditText)
        noTeleponEditText = findViewById(R.id.noTeleponEditText)
        buttonUpdate = findViewById<Button>(R.id.buttonUpdate)
        
        updateData()
    }
    private fun updateData() {
        val bundle = intent.getBundleExtra("dataMember")
        if (bundle != null) {
            idMemberUpdateEditText.setText(bundle.getString("idMember"))
            usernameEditText.setText(bundle.getString("username"))
            nameEditText.setText(bundle.getString("name"))
            tempatLahirEditText.setText(bundle.getString("tempatLahir"))
            tanggalLahirEditText.setText(bundle.getString("tanggalLahir"))
            noTeleponEditText.setText(bundle.getString("noTelepon"))

            buttonUpdate.setOnClickListener{
                val url1: String =AppConfig().IP_SERVER + "/PHP/updateMember.php"
                val stringRequest = object : StringRequest(Method.POST, url1,
                    Response.Listener { response ->
                        val jsonObj = JSONObject(response)
                        Toast.makeText(this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@UpdateDataMember, ViewDataMember::class.java)
                        startActivity(intent)
                        finish()
                    },
                    Response.ErrorListener { _ ->
                        Toast.makeText(this, "Gagal Terhubung", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): HashMap<String, String> {
                        val params = HashMap<String, String>()
                        params["idMember"]      = idMemberUpdateEditText.text.toString()
                        params["username"]      = usernameEditText.text.toString()
                        params["name"]          = nameEditText.text.toString()
                        params["tempatLahir"]   = tempatLahirEditText.text.toString()
                        params["tanggalLahir"]  = tanggalLahirEditText.text.toString()
                        params["noTelepon"]     = noTeleponEditText.text.toString()

                        return params
                    }
                }
                Volley.newRequestQueue(this).add(stringRequest)
            }
        }
    }
    fun backMember(){
        val intent = Intent(this@UpdateDataMember, ViewDataMember::class.java)
        startActivity(intent)
        finish()
    }
}
