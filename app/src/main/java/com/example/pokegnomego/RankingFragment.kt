package com.example.pokegnomego

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokegnomego.databinding.FragmentRankingBinding
import com.example.pokegnomego.network.ApiClient
import com.example.pokegnomego.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.pokegnomego.models.RankingUser

class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding
    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankingBinding.inflate(inflater, container, false)

        setupRecyclerView()
        loadRankingData()

        return binding.root
    }

    private fun setupRecyclerView() {
        rankingAdapter = RankingAdapter()
        binding.rankingRecyclerView.adapter = rankingAdapter
        binding.rankingRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun loadRankingData() {
        apiService = ApiClient.client.create(ApiService::class.java)
        apiService.getUsers().enqueue(object : Callback<List<RankingUser>> {
            override fun onResponse(call: Call<List<RankingUser>>, response: Response<List<RankingUser>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("RankingFragment", "Data received: $it")
                        rankingAdapter.setRankingList(it)
                    } ?: run {
                        Log.e("RankingFragment", "No data received")
                    }
                } else {
                    Log.e("RankingFragment", "Failed to load data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RankingUser>>, t: Throwable) {
                Log.e("RankingFragment", "Failed to load data", t)
            }
        })
    }
}
