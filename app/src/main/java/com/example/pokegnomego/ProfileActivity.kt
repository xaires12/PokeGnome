package com.example.pokegnomego

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.pokegnomego.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonBack2.setOnClickListener {
            Log.d("ProfileActivity", "Back button clicked")
            onBackPressed()
        }

        binding.buttonMyPhotos.setOnClickListener {
            Log.d("ProfileActivity", "My Photos button clicked")
            navigateToFragment(MyPhotosFragment())
        }

        binding.buttonRank.setOnClickListener {
            Log.d("ProfileActivity", "Ranking button clicked")
            navigateToFragment(RankingFragment())
        }

        binding.buttonMyAchievements.setOnClickListener {
            Log.d("ProfileActivity", "My Achievements button clicked")
            navigateToFragment(AchievementsFragment())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        Log.d("ProfileActivity", "Navigating to fragment: ${fragment::class.java.simpleName}")
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}