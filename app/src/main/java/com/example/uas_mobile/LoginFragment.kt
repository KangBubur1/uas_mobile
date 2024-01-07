import android.content.Intent
import android.os.Bundle
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

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            performLogin(username, password)
        }

        return view
    }

    private fun performLogin(username: String, password: String) {
        val url = "http://192.168.0.105/PHP/login.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                println("Server Response: $response")

                val parts = response.split("|")
                if (parts.size == 2 && parts[0] == "Success") {
                    val userStatus = parts[1]

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

    private fun isUserAdmin(status: String): Boolean {
        return status.equals("admin", ignoreCase = true)
    }
}
