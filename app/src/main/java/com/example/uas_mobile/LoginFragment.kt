import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.uas_mobile.Admin.AdminNav
import com.example.uas_mobile.AppConfig
import com.example.uas_mobile.HomeActivity
import com.example.uas_mobile.R
import com.example.uas_mobile.RegisterActivity
import org.json.JSONException
import org.json.JSONObject

class LoginFragment : Fragment() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize views
        etUsername = view.findViewById(R.id.etUsername)
        etPassword = view.findViewById(R.id.etPassword)
        btnLogin = view.findViewById(R.id.btnLogin)

        // Pengecekan apakah user sudah login
        checkUserLoggedIn()

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            performLogin(username, password)
        }

        return view
    }

    private fun checkUserLoggedIn() {
        val sharedPreferences = requireContext().getSharedPreferences("loginPref",0)
        val username = sharedPreferences.getString("username", "")

        if(!username.isNullOrEmpty()) {
            val userStatus = sharedPreferences.getString("userStatus", "")
            val intent = if (isUserAdmin(userStatus!!)) {
                Intent(requireContext(), AdminNav::class.java)
            } else {
                Intent(requireContext(), HomeActivity::class.java)
            }

            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun performLogin(username: String, password: String) {
        val url = AppConfig().IP_SERVER + "/PHP/login.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                println("Server Response: $response")

                val parts = response.split("|")
                if (parts.size == 8 && parts[0] == "Success") {
                    val userStatus = parts[1]
                    val name = parts[2]
                    val password = parts[3]
                    val tempatLahir = parts[4]
                    val tanggalLahir = parts[5]
                    val noTelepon = parts[6]
                    val idMember = parts[7]

                    // Simpan informasi login
                    saveLoginInfo(username, userStatus, name, password, tempatLahir, tanggalLahir, noTelepon, idMember)

                    Log.d("LoginFragment", "Saved login information: $username, $userStatus, $name, $password, $tempatLahir, $tanggalLahir, $noTelepon, $idMember")

                    val intent = if (isUserAdmin(userStatus)) {
                        Intent(requireContext(), AdminNav::class.java)
                    } else {
                        Intent(requireContext(), HomeActivity::class.java)
                    }

                    startActivity(intent)
                    requireActivity().finish()
                } else {

                    // Handle invalid response format or login failure
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }

        Volley.newRequestQueue(requireContext()).add(stringRequest)
    }


    // Ke Page Registrasi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtRegistrasi = view.findViewById<TextView>(R.id.txtRegistrasi)

        txtRegistrasi.setOnClickListener {
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Menggunakan Sharedpreferences
    private fun saveLoginInfo(username: String, userStatus: String, name: String, password: String, tempatLahir: String, tanggalLahir: String, noTelepon: String, idMember: String ) {
        val sharedPreferences = requireContext().getSharedPreferences("loginPref",0)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("userStatus", userStatus)
        editor.putString("name", name)
        editor.putString("password", password)
        editor.putString("tempatLahir", tempatLahir)
        editor.putString("tanggalLahir", tanggalLahir)
        editor.putString("noTelepon", noTelepon)
        editor.putString("idMember",idMember)
        editor.apply()
    }

    private fun isUserAdmin(status: String): Boolean {
        return status.equals("admin", ignoreCase = true)
    }
}
