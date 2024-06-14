package com.example.pokegnomego

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pokegnomego.network.ApiClient
import com.example.pokegnomego.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondFragment : Fragment() {

    private lateinit var resultTextView: TextView
    private lateinit var drawGnomeButton: Button
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        resultTextView = view.findViewById(R.id.resultTextView)
        drawGnomeButton = view.findViewById(R.id.button_second)

        drawGnomeButton.setOnClickListener {
            drawGnomeForUser(userId)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("userId", -1)
    }

    private fun drawGnomeForUser(userId: Int) {
        if (userId == -1) {
            resultTextView.text = "Invalid user ID"
            return
        }

        val apiService = ApiClient.client.create(ApiService::class.java)
        apiService.drawGnome(userId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    resultTextView.text = "Gnome drawn successfully: ${response.body()?.string()}"
                } else {
                    resultTextView.text = "Failed to draw gnome"
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                resultTextView.text = "Error: ${t.message}"
            }
        })
    }
}
