package com.dicoding.jobspark.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.jobspark.R
import com.dicoding.jobspark.data.remote.Job
import com.dicoding.jobspark.ui.adapter.JobAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SavedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_saved)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_saved)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = getSharedPreferences("SAVED_JOBS", Context.MODE_PRIVATE)
        val gson = Gson()
        val savedJobsJson = sharedPreferences.getString("SAVED_JOBS_LIST", "[]")
        val jobListType = object : TypeToken<List<Job>>() {}.type
        val savedJobs: List<Job> = gson.fromJson(savedJobsJson, jobListType)

        val jobAdapter =
            JobAdapter(savedJobs.toMutableList(), isSimplified = false, isEditable = true)
        recyclerView.adapter = jobAdapter

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.history -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.saved -> {
                    if (this::class.java != SavedActivity::class.java) {
                        val intent = Intent(this, SavedActivity::class.java)
                        startActivity(intent)
                    }
                    true
                }

                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.saved
    }
}
