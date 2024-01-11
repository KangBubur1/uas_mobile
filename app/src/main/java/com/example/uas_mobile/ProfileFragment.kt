package com.example.uas_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.uas_mobile.User.UserEditProfile
import com.google.android.material.button.MaterialButton


class ProfileFragment : Fragment() {
    private lateinit var tvName: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        // Logout
        val logout: MaterialButton = view.findViewById(R.id.btnLogout)
        logout.setOnClickListener {
            logout()
        }

        val editProfile: AppCompatButton = view.findViewById(R.id.btnEditProfile)
        editProfile.setOnClickListener{
            editProfile()
        }

        //Name
        tvName = view.findViewById(R.id.txtname)

        updateUIPreferences()
        return view
    }

    private fun updateUIPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("loginPref",0)
        val name = sharedPreferences.getString("name", "") ?: ""
        tvName.text = name
    }

    private fun getLoggedinUserData(): Triple<String, String, String> {
        val sharedPreferences = requireContext().getSharedPreferences("loginPref", 0)
        val username = sharedPreferences.getString("username", "") ?: ""
        val userStatus = sharedPreferences.getString("userStatus", "") ?: ""
        val name = sharedPreferences.getString("name", "") ?: ""
        return Triple(username, userStatus, name)
    }


    private fun logout() {

        Log.d("ProfileFragment", "Logout button clicked")
        val sharedPreferences = requireContext().getSharedPreferences("loginPref",0)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun editProfile() {
        val intent = Intent(requireContext(), UserEditProfile::class.java)
        startActivity(intent)
    }

}