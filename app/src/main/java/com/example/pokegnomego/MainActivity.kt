package com.example.pokegnomego

import android.content.Context
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

class MainActivity : AppCompatActivity() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val NAME_KEY = "name_key"
        const val PASSWORD_KEY = "password_key"
        const val TAG = "MainActivity"
    }

    private lateinit var sharedpreferences: SharedPreferences
    private var usrname: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usrnameEdit = findViewById<EditText>(R.id.username)
        val passwordEdit = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        usrname = sharedpreferences.getString(NAME_KEY, null)
        password = sharedpreferences.getString(PASSWORD_KEY, null)

        loginButton.setOnClickListener {
            val username = usrnameEdit.text.toString()
            val password = passwordEdit.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@MainActivity, "Wpisz login i hasło", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(User(0, username, password))
            }
        }

        registerButton.setOnClickListener {
            val i = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    private fun loginUser(user: User) {
        val apiService = ApiClient.client.create(ApiService::class.java)
        apiService.login(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.string()?.let {
                        Log.d(TAG, "Server response: $it")
                        try {
                            val jsonObject = JSONObject(it)
                            val userId = jsonObject.getInt("userId")
                            saveUserId(userId)
                            Toast.makeText(this@MainActivity, "Login successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                            finish()
                        } catch (e: Exception) {
                            Log.e(TAG, "Error parsing server response", e)
                            Toast.makeText(this@MainActivity, "Invalid response from server", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e(TAG, "Server response not successful: ${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Login request failed", t)
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveUserId(userId: Int) {
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", userId)
        editor.apply()
    }

    override fun onStart() {
        super.onStart()
        if (usrname != null && password != null) {
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
