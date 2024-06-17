package com.example.pokegnomego.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.AchievementAdapter
import com.example.pokegnomego.R
import com.example.pokegnomego.network.ApiClient
import com.example.pokegnomego.network.ApiService
import com.example.pokegnomego.models.UserAchievements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AchievementsFragment : Fragment() {

    private lateinit var achievementRecyclerView: RecyclerView
    private lateinit var adapter: AchievementAdapter
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_achievements, container, false)
        achievementRecyclerView = view.findViewById(R.id.achievementRecyclerView)
        achievementRecyclerView.layoutManager = LinearLayoutManager(context)

        val sharedPreferences = context?.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences?.getInt("userId", -1) ?: -1

        fetchAchievements()

        return view
    }

    private fun fetchAchievements() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = ApiClient.client.create(ApiService::class.java)
            try {
                val response = apiService.getUserAchievements(userId).execute()
                if (response.isSuccessful) {
                    val userAchievements = response.body()
                    if (userAchievements != null) {
                        withContext(Dispatchers.Main) {
                            adapter = AchievementAdapter(userAchievements.achievements)
                            achievementRecyclerView.adapter = adapter
                        }
                    } else {
                        Log.e("AchievementFragment", "No data received")
                    }
                } else {
                    Log.e("AchievementFragment", "Failed to load data: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("AchievementFragment", "Failed to load data", e)
            }
        }
    }
}