package com.dicoding.jobspark.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.jobspark.R
import com.dicoding.jobspark.data.remote.RegisterRequest
import com.dicoding.jobspark.data.remote.RegisterResponse
import com.dicoding.jobspark.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InterestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_last)

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val fullName = intent.getStringExtra("full_name")
        val birthDate = intent.getStringExtra("birth_date")
        val gender = intent.getStringExtra("gender")
        val address = intent.getStringExtra("address")
        val emergencyContact =
            intent.getStringExtra("emergency_contact")

        val hobbySpinner = findViewById<Spinner>(R.id.hobbySpinner)
        val specialSkillSpinner = findViewById<Spinner>(R.id.specialSkillSpinner)
        val healthConditionSpinner = findViewById<Spinner>(R.id.healthConditionSpinner)

        val hobbies = arrayOf("Reading", "Gaming", "Traveling", "Music")
        val specialSkills = arrayOf("Programming", "Designing", "Marketing")
        val healthConditions = arrayOf("Healthy", "Sick", "Recovering")

        val hobbyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hobbies)
        val specialSkillAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, specialSkills)
        val healthConditionAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, healthConditions)

        hobbyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        specialSkillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        healthConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        hobbySpinner.adapter = hobbyAdapter
        specialSkillSpinner.adapter = specialSkillAdapter
        healthConditionSpinner.adapter = healthConditionAdapter

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val hobby = hobbySpinner.selectedItem.toString()
            val specialSkill = specialSkillSpinner.selectedItem.toString()
            val healthCondition = healthConditionSpinner.selectedItem.toString()

            if (hobby.isNotEmpty() && specialSkill.isNotEmpty() && healthCondition.isNotEmpty() &&
                address != null && emergencyContact != null
            ) {
                if (email != null && password != null && fullName != null && birthDate != null && gender != null) {
                    registerUser(
                        email, password, fullName, birthDate, gender,
                        hobby, specialSkill, healthCondition, address, emergencyContact
                    )
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(
        email: String,
        password: String,
        fullName: String,
        birthDate: String,
        gender: String,
        hobby: String,
        specialSkill: String,
        healthCondition: String,
        address: String,
        emergencyContact: String
    ) {
        val registerRequest = RegisterRequest(
            full_name = fullName,
            email = email,
            password = password,
            about_me = "About me info",
            birth_date = birthDate,
            gender = gender,
            address = address,
            emergency_number = emergencyContact,
            profile_img = "https://example.com/profile.jpg",
            hobby = hobby,
            interest = specialSkill,
            special_ability = specialSkill,
            health_condition = healthCondition
        )

        RetrofitClient.instance.registerUser(registerRequest)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val registerResponse = response.body()
                        if (registerResponse != null) {
                            Log.d(
                                "Register",
                                "Registration successful: ${registerResponse.message}"
                            )
                            Toast.makeText(
                                this@InterestActivity,
                                "Registration successful: ${registerResponse.message}",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@InterestActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@InterestActivity,
                                "Received empty response body",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@InterestActivity,
                            "Registration failed: ${response.errorBody()?.string()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(
                        this@InterestActivity,
                        "Network error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
