package com.example.uas_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley



class SendData : AppCompatActivity() {
    // ... (other existing code)
    private lateinit var editTextKodeBuku: EditText
    private lateinit var action: String
    private lateinit var editTextJudulBuku: EditText
    private lateinit var editTextPengarang: EditText
    private lateinit var editTextPenerbit: EditText
    private lateinit var editTextTempatTerbit: EditText
    private lateinit var editTextJumlahSalinan: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button
    private lateinit var buttonBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // ... (other existing code)

        // Get references to your TextInputEditText fields
        editTextKodeBuku = findViewById<EditText>(R.id.editTextKodeBuku)
        editTextJudulBuku = findViewById<EditText>(R.id.editTextJudulBuku)
        editTextPengarang = findViewById<EditText>(R.id.editTextPengarang)
        editTextPenerbit = findViewById<EditText>(R.id.editTextPenerbit)
        editTextTempatTerbit = findViewById<EditText>(R.id.editTextTempatTerbit)
        editTextJumlahSalinan = findViewById<EditText>(R.id.editTextJumlahSalinan)
        buttonAdd = findViewById<Button>(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            kirimdata()
        }

        // Use AsyncTask or another background process to avoid NetworkOnMainThreadException

    }
    private fun kirimdata() {
        val url = "http://192.168.100.33/uas_mobile/PHP/inputBuku.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["kodeBuku"] = editTextKodeBuku.text.toString()
                params["judulBuku"] = editTextJudulBuku.text.toString()
                params["pengarang"] = editTextPengarang.text.toString()
                params["penerbit"] = editTextPenerbit.text.toString()
                params["tempatTerbit"] = editTextTempatTerbit.text.toString()
                params["jumlahSalinan"] = editTextJumlahSalinan.text.toString()
                return params
            }
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }
    // Handle image selection logic

    // Handle the result from the image picker
}





