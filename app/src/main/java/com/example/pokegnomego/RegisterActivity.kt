package com.example.pokegnomego

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokegnomego.models.User
import com.example.pokegnomego.network.ApiClient
import com.example.pokegnomego.network.ApiService
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.pokegnomego.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val NAME_KEY = "name_key"
        const val PASSWORD_KEY = "password_key"
        const val TAG = "RegisterActivity"
    }

    private lateinit var sharedpreferences: SharedPreferences
    private var usrname: String? = null
    private var password: String? = null
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usrnameEdit = findViewById<EditText>(R.id.usrname)
        val passwordEdit = findViewById<EditText>(R.id.passwd)
        val registerBut = findViewById<Button>(R.id.register_but)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        usrname = sharedpreferences.getString(NAME_KEY, null)
        password = sharedpreferences.getString(PASSWORD_KEY, null)

        registerBut.setOnClickListener {
            val username = usrnameEdit.text.toString()
            val password = passwordEdit.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Wpisz username i hasło", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(User(0, username, password))
            }
        }
    }

    private fun registerUser(user: User) {
        val apiService = ApiClient.client.create(ApiService::class.java)
        apiService.createUser(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.string()?.let {
                        Log.d(TAG, "Server response: $it")
                        try {
                            val jsonObject = JSONObject(it)
                            val message = jsonObject.getString("message")
                            Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                            // Transition to MainActivity (login screen)
                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                            finish()
                        } catch (e: Exception) {
                            Log.e(TAG, "Error parsing server response", e)
                            Toast.makeText(this@RegisterActivity, "Invalid response from server", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e(TAG, "Server response not successful: ${response.errorBody()?.string()}")
                    Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Registration request failed", t)
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
