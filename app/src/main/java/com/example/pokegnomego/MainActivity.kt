package com.example.pokegnomego

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.pokegnomego.databinding.ActivityMainBinding
import com.example.pokegnomego.network.ApiClient
import com.example.pokegnomego.network.ApiService
import com.example.pokegnomego.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listeners for buttons
        binding.buttonFirst.setOnClickListener {
            loadFragment(SecondFragment())
        }

        binding.buttonAchievements.setOnClickListener {
            loadFragment(ThirdFragment())
        }

        binding.buttonMap.setOnClickListener {
            val intent = Intent(this@MainActivity, MapActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        apiService = ApiClient.client.create(ApiService::class.java)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.whatever, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun fetchGnomes() {
        apiService.getGnomes().enqueue(object : Callback<List<Gnome>> {
            override fun onResponse(call: Call<List<Gnome>>, response: Response<List<Gnome>>) {
                if (response.isSuccessful) {
                    val gnomes = response.body()
                    // Handle the list of gnomes
                }
            }

            override fun onFailure(call: Call<List<Gnome>>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun fetchGnomeById(gnomeId: Int) {
        apiService.getGnome(gnomeId).enqueue(object : Callback<Gnome> {
            override fun onResponse(call: Call<Gnome>, response: Response<Gnome>) {
                if (response.isSuccessful) {
                    val gnome = response.body()
                    // Handle the gnome
                }
            }

            override fun onFailure(call: Call<Gnome>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun fetchUsers() {
        apiService.getUsers().enqueue(object : Callback<List<RankingUser>> {
            override fun onResponse(call: Call<List<RankingUser>>, response: Response<List<RankingUser>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    // Handle the list of users
                }
            }

            override fun onFailure(call: Call<List<RankingUser>>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun addVisit(visit: Visit) {
        apiService.addVisit(visit).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful visit addition
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun fetchVisitById(visitId: Int) {
        apiService.getVisit(visitId).enqueue(object : Callback<Visit> {
            override fun onResponse(call: Call<Visit>, response: Response<Visit>) {
                if (response.isSuccessful) {
                    val visit = response.body()
                    // Handle the visit
                }
            }

            override fun onFailure(call: Call<Visit>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun addComment(gnomeId: Int, comment: Comment) {
        apiService.addComment(gnomeId, comment).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful comment addition
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun fetchGnomeComments(gnomeId: Int) {
        apiService.getGnomeComments(gnomeId).enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val comments = response.body()
                    // Handle the list of comments
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun createUser(user: User) {
        apiService.createUser(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful user creation
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun loginUser(user: User) {
        apiService.login(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful login
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun fetchUserVisits(userId: Int) {
        apiService.getUserVisits(userId).enqueue(object : Callback<UserVisits> {
            override fun onResponse(call: Call<UserVisits>, response: Response<UserVisits>) {
                if (response.isSuccessful) {
                    val visits = response.body()
                    // Handle the user visits
                }
            }

            override fun onFailure(call: Call<UserVisits>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun fetchAchievements() {
        apiService.getAchievements().enqueue(object : Callback<List<Achievement>> {
            override fun onResponse(call: Call<List<Achievement>>, response: Response<List<Achievement>>) {
                if (response.isSuccessful) {
                    val achievements = response.body()
                    // Handle the achievements
                }
            }

            override fun onFailure(call: Call<List<Achievement>>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun fetchUserAchievements(userId: Int) {
        apiService.getUserAchievements(userId).enqueue(object : Callback<UserAchievements> {
            override fun onResponse(call: Call<UserAchievements>, response: Response<UserAchievements>) {
                if (response.isSuccessful) {
                    val achievements = response.body()
                    // Handle the user achievements
                }
            }

            override fun onFailure(call: Call<UserAchievements>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun drawGnomeForUser(userId: Int) {
        apiService.drawGnomeForUser(userId).enqueue(object : Callback<Gnome> {
            override fun onResponse(call: Call<Gnome>, response: Response<Gnome>) {
                if (response.isSuccessful) {
                    val gnome = response.body()
                    // Handle the drawn gnome
                }
            }

            override fun onFailure(call: Call<Gnome>, t: Throwable) {
                // Handle the error
            }
        })
    }
}
