package com.example.pokegnomego

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pokegnomego.models.GnomeResponse
import com.example.pokegnomego.network.ApiClient
import com.example.pokegnomego.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondFragment : Fragment() {

    private lateinit var resultTextView: TextView
    private lateinit var drawGnomeButton: Button
    private var user_id: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        user_id = sharedPreferences.getInt("userId", -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        resultTextView = view.findViewById(R.id.resultTextView)
        drawGnomeButton = view.findViewById(R.id.button_second)

        drawGnomeButton.setOnClickListener {
            drawGnomeForUser(user_id)
        }

        return view
    }

    private fun drawGnomeForUser(user_id: Int) {
        val apiService = ApiClient.client.create(ApiService::class.java)

        apiService.drawGnome(user_id).enqueue(object : Callback<GnomeResponse> {
            override fun onResponse(call: Call<GnomeResponse>, response: Response<GnomeResponse>) {
                if (response.isSuccessful) {
                    val gnomeResponse = response.body()
                    if (gnomeResponse != null) {
                        resultTextView.text = "Gnome drawn successfully: ${gnomeResponse.name}"
                        saveCoordinates(gnomeResponse.latitude, gnomeResponse.longitude)
                    } else {
                        resultTextView.text = "Failed to draw gnome"
                    }
                } else {
                    resultTextView.text = "Failed to draw gnome"
                    Log.e("drawGnomeForUser", "Response is not successful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GnomeResponse>, t: Throwable) {
                resultTextView.text = "Error: ${t.message}"
                Log.e("drawGnomeForUser", "API call failed", t)
            }
        })
    }

    private fun saveCoordinates(latitude: Double, longitude: Double) {
        val sharedPreferences = requireContext().getSharedPreferences("CoordsPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Latitude", latitude.toString())
        editor.putString("Longitude", longitude.toString())
        editor.apply()
    }
}
