package com.example.uas_mobile


import LoginFragment
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
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RegisterActivity: AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRePassword: EditText
    private lateinit var etTempatLahir: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var etNoTelepon: EditText
    private lateinit var btnRegistrasi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val datePicker: EditText = findViewById(R.id.txtTanggalLahir)
        val materialBuilder = MaterialDatePicker.Builder.datePicker()
        materialBuilder.setTitleText("Select Date of Birth")

        val materialDatePicker = materialBuilder.build()

        materialDatePicker.addOnPositiveButtonClickListener(object : MaterialPickerOnPositiveButtonClickListener<Long>{
            override fun onPositiveButtonClick(selection: Long) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selection

                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate: String = dateFormat.format(Date(selection))

                datePicker.setText(formattedDate)
            }
        })

        datePicker.setOnClickListener{
            materialDatePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        etUsername = findViewById(R.id.usernameInputEditText)
        etName = findViewById(R.id.nameInputEditText)
        etPassword = findViewById(R.id.passwordInputEditText)
        etRePassword = findViewById(R.id.rePasswordInputEditText)
        etTempatLahir = findViewById(R.id.passwordInputEditText)
        etTanggalLahir = findViewById(R.id.txtTanggalLahir)
        etNoTelepon = findViewById(R.id.noTeleponInputEditText)
        btnRegistrasi = findViewById(R.id.btnRegistrasi)

        btnRegistrasi.setOnClickListener {
            val username = etUsername.text.toString()
            val name = etName.text.toString()
            val password = etPassword.text.toString()
            val rePassword = etRePassword.text.toString()
            val tempatLahir = etTempatLahir.text.toString()
            val tanggalLahir = etTanggalLahir.text.toString()
            val noTelepon = etNoTelepon.text.toString().toInt()

            if ( password == rePassword ){
                performRegis(username,name,password, rePassword, tempatLahir, tanggalLahir, noTelepon)
            } else {
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT)
            }


        }
    }

    private fun performRegis(username: String, name: String, password: String, rePassword: String, tempatLahir: String, tanggalLahir: String, noTelepon: Int ){
        val url = "http://192.168.100.33/uas_mobile/PHP/register.php"

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                if (response == "Success") {
                    val intent = Intent(this, LoginFragment::class.java)
                    startActivity(intent)
                    finish()
                }
            },
            Response.ErrorListener {error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["username"] = username
                params["name"] = name
                params["password"] = password
                params["tempatLahir"] = tempatLahir
                params["tanggalLahir"] = tanggalLahir
                params["noTelepon"] = noTelepon.toString()
                params["tanggalRegistrasi"] = currentDate.toString()
                return params
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)

    }

}