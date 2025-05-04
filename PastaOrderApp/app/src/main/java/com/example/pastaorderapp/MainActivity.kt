package com.example.pastaorderapp

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pastaorderapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val componentName = ComponentName(this, MainActivity::class.java)

            if (activityManager.lockTaskModeState == ActivityManager.LOCK_TASK_MODE_NONE) {
                startLockTask()
            }
        }

        setupNavigation()
    }
    private fun setupNavigation() {
        val navGraphId = R.navigation.oreders_nav_graph
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.viewCont) as? NavHostFragment
            ?: NavHostFragment.create(navGraphId)

        supportFragmentManager.beginTransaction()
            .replace(R.id.viewCont, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commitNow()

        navController = navHostFragment.navController
    }

}
data class Product(
    val id: String,
    val name: String
)