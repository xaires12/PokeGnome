package com.example.pokegnomego

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.pokegnomego.databinding.ActivityLoginBinding
import com.example.pokegnomego.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val NAME_KEY = "name_key"
        const val PASSWORD_KEY = "password_key"
    }
    private lateinit var sharedpreferences: SharedPreferences

    public fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.whatever, fragment)
        transaction.commit()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //działa z poziomu aktywności
        loadFragment(FirstFragment())

        sharedpreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFirst.setOnClickListener {
            loadFragment(SecondFragment())
        }

        binding.buttonAchievements.setOnClickListener {
            loadFragment(ThirdFragment())
        }

        binding.buttonMap.setOnClickListener {
            val i = Intent(this@LoginActivity, MapActivity::class.java)
            // on below line we are calling start activity method to start our activity.
            startActivity(i)
            // on below line we are calling finish to finish our main activity.
            finish()
        }

        binding.buttonProfile.setOnClickListener {
            val i = Intent(this@LoginActivity, ProfileActivity::class.java)
            // on below line we are calling start activity method to start our activity.
            startActivity(i)
            // on below line we are calling finish to finish our main activity.
            finish()
        }

        binding.buttonLogout.setOnClickListener {
            val editor = sharedpreferences.edit()

            editor.clear()

            editor.apply()

            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}