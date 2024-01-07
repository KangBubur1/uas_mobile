package com.example.uas_mobile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException


class SendData : AppCompatActivity() {
    // ... (other existing code)
    private lateinit var editTextKodeBuku: EditText
    private lateinit var action: String
    private lateinit var editTextJudulBuku: EditText
    private lateinit var editTextPengarang: EditText
    private lateinit var editTextPenerbit: EditText
    private lateinit var editTextTempatTerbit: EditText
    private lateinit var editTextJumlahSalinan: EditText
    private lateinit var imageButtonGambarBuku  : ImageView
    private lateinit var editTextKodeKategori: EditText
    private var resId     = 0
    private lateinit var bitmap     : Bitmap

    private lateinit var buttonAdd: Button
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button
    private lateinit var buttonDisplay    : Button
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
        imageButtonGambarBuku   = findViewById(R.id.imageButtonGambarBuku)
        editTextKodeKategori = findViewById<EditText>(R.id.editTextKodeKategori)

        buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonEdit = findViewById<Button>(R.id.buttonEdit)
        buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDisplay = findViewById<Button>(R.id.buttonDisplay)

        imagePick()
        buttonAdd.setOnClickListener {
            kirimdata()
//            editTextKodeBuku.setText("")
        }
        buttonEdit.setOnClickListener{
            updatedata()
        }
        buttonDelete.setOnClickListener {
                val kodeBuku = editTextKodeBuku.text.toString()
            deleteData(kodeBuku)
        }
        buttonDisplay.setOnClickListener {
            displayData()
        }
    }
    private fun imagePick() {
        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data!!
                val uri = data.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    imageButtonGambarBuku.setImageBitmap(bitmap)
                    resId = 1
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        imageButtonGambarBuku.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            activityResultLauncher.launch(intent)
        }
    }



    private fun updateData() {
        val bundle = intent.getBundleExtra("buku")
        if (bundle != null) {
            Picasso.get().load(bundle.getString("gambarBuku")).into(imageButtonGambarBuku)
            //visible edit button and hide save button

        }
    }

    private fun kirimdata() {
        if (resId == 1) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val base64Image = Base64.encodeToString(bytes, Base64.DEFAULT)
            val url = "http://192.168.100.33/PHP/inputBuku.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener { response ->
                Log.d("Response", "Server Response: $response")
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
                    params["gambarBuku"] = base64Image
                    params["kodeKategori"] = editTextKodeKategori.text.toString()
                    return params
                }

                override fun getBodyContentType(): String {
                    return "application/x-www-form-urlencoded; charset=UTF-8"
                }
            }
            Volley.newRequestQueue(this).add(stringRequest)
        }
    }

    private fun updatedata() {
        if (resId == 1) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val base64Image = Base64.encodeToString(bytes, Base64.DEFAULT)
            val url = "http://192.168.100.33/PHP/updateBuku.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener { response ->
                    Log.d("Response", "Server Response: $response")
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
                    params["gambarBuku"] = base64Image
                    params["kodeKategori"] = editTextKodeKategori.text.toString()
                    return params
                }

                override fun getBodyContentType(): String {
                    return "application/x-www-form-urlencoded; charset=UTF-8"
                }
            }
            Volley.newRequestQueue(this).add(stringRequest)
        }
    }
    private fun deleteData(kodeBuku: String) {
        val url = "http://192.168.100.33/PHP/deleteBuku.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Log.d("Response", "Server Response: $response")

                val jsonObj = JSONObject(response)
                Toast.makeText(this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["kodeBuku"] = kodeBuku
                return params
            }

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)
    }
    private fun displayData(){
        val intent = Intent(this@SendData, DisplayActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }

}





