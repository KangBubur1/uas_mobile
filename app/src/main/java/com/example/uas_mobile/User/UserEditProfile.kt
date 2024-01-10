package com.example.uas_mobile.User

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.uas_mobile.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
class UserEditProfile : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etTempatLahir: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var etNoTelepon: EditText

    // Declare MaterialDatePicker as a field
    private lateinit var materialDatePicker: MaterialDatePicker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_edit_profile)

        etName = findViewById(R.id.nameInputEditText)
        etTempatLahir = findViewById(R.id.tempatLahirInputEditText)
        etTanggalLahir = findViewById(R.id.txtTanggalLahir)
        etNoTelepon = findViewById(R.id.noTeleponInputEditText)

        // Retrieve user data from SharedPreferences
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("loginPref", 0)

        val name = sharedPreferences.getString("name", "")
        val tempatLahir = sharedPreferences.getString("tempatLahir", "")
        val tanggalLahir = sharedPreferences.getString("tanggalLahir", "")
        val noTelepon = sharedPreferences.getString("noTelepon", "")

        // Populate EditText fields with existing user data
        etName.setText(name)
        etTempatLahir.setText(tempatLahir)
        etNoTelepon.setText(noTelepon)

        // Set up date picker
        val datePicker: EditText = findViewById(R.id.txtTanggalLahir)
        var materialBuilder = MaterialDatePicker.Builder.datePicker()
        materialBuilder.setTitleText("Select Date of Birth")

        materialDatePicker = materialBuilder.build()

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

        // Set the initial date value if available
        if (tanggalLahir != null && tanggalLahir.isNotEmpty()) {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(tanggalLahir)
            val timeInMillis = date?.time ?: Calendar.getInstance().timeInMillis

            // Recreate MaterialDatePicker with the new selection
            materialBuilder.setSelection(timeInMillis)
            materialDatePicker = materialBuilder.build()
        }

    }
}