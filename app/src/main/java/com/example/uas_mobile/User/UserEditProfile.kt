package com.example.uas_mobile.User

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.HomeActivity
import com.example.uas_mobile.ProfileFragment
import com.example.uas_mobile.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class UserEditProfile : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etTempatLahir: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var etNoTelepon: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRePassword: EditText


    // Declare MaterialDatePicker as a field
    private lateinit var materialDatePicker: MaterialDatePicker<Long>
    private lateinit var idMember: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_edit_profile)
        etName = findViewById(R.id.nameInputEditText)
        etTempatLahir = findViewById(R.id.tempatLahirInputEditText)
        etTanggalLahir = findViewById(R.id.txtTanggalLahir)
        etNoTelepon = findViewById(R.id.noTeleponInputEditText)
        etPassword = findViewById(R.id.passwordInputEditText)
        etRePassword = findViewById(R.id.rePasswordInputEditText)

        val btnUpdate = findViewById<MaterialButton>(R.id.btnUpdate)

        // Retrieve user data from SharedPreferences
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("loginPref", 0)

        idMember = sharedPreferences.getString("idMember", "") ?: ""

        val name = sharedPreferences.getString("name", "")
        val tempatLahir = sharedPreferences.getString("tempatLahir", "")
        val tanggalLahir = sharedPreferences.getString("tanggalLahir", "")
        val noTelepon = sharedPreferences.getString("noTelepon", "")
        Log.d("UserEditProfile", "$name,$tempatLahir,$tanggalLahir,$noTelepon")

        // Populate EditText fields with existing user data
        etName.setText(name)
        etTempatLahir.setText(tempatLahir)
        etNoTelepon.setText(noTelepon)


        // Set up date picker
        val datePicker: EditText = findViewById(R.id.txtTanggalLahir)
        var materialBuilder = MaterialDatePicker.Builder.datePicker()
        materialBuilder.setTitleText("Select Date of Birth")

        materialDatePicker = materialBuilder.build()

        datePicker.setText(tanggalLahir)

        materialDatePicker.addOnPositiveButtonClickListener(object :
            MaterialPickerOnPositiveButtonClickListener<Long> {
            override fun onPositiveButtonClick(selection: Long) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selection

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate: String = dateFormat.format(calendar.time)

                datePicker.setText(formattedDate)
            }
        })

        datePicker.setOnClickListener {
            materialDatePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        btnUpdate.setOnClickListener {
            val inputName = etName.text.toString()
            val inputPassword = etPassword.text.toString()
            val inputRepassword = etRePassword.text.toString()
            val inputTempatlahir = etTempatLahir.text.toString()
            val inputTanggallahir = etTanggalLahir.text.toString()
            val inputNotelepon = etNoTelepon.text.toString()
            if (inputName.isBlank() || inputPassword.isBlank() || inputRepassword.isBlank() || inputTempatlahir.isBlank() || inputTanggallahir.isBlank() || inputNotelepon.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (inputPassword != inputRepassword) {
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateData()
        }

    }

    private fun updateData() {
        val url = AppConfig().IP_SERVER + "/PHP/updateProfile.php"
        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener { response ->
                val jsonObj = JSONObject(response)

                //Update preferences
                updatePreferences()

                Toast.makeText(this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            },
            Response.ErrorListener { _ ->
                Toast.makeText(this, "Gagal Terhubung", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["idMember"] = idMember
                params["password"] = etPassword.text.toString()
                params["name"] = etName.text.toString()
                params["tempatLahir"] = etTempatLahir.text.toString()
                params["tanggalLahir"] = etTanggalLahir.text.toString()
                params["noTelepon"] = etNoTelepon.text.toString()
                return params
            }
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun updatePreferences() {
        val sharedPreferences = getSharedPreferences("loginPref", 0)
        val editor = sharedPreferences.edit()
        editor.putString("name", etName.text.toString())
        editor.putString("password", etPassword.text.toString())
        editor.putString("tempatLahir", etTempatLahir.text.toString())
        editor.putString("tanggalLahir", etTanggalLahir.text.toString())
        editor.putString("noTelepon", etNoTelepon.text.toString())
        editor.apply()
    }


}