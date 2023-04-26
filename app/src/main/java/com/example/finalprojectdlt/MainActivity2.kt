package com.example.finalprojectdlt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalprojectdlt.databinding.ActivityDashboardUserBinding
import com.example.finalprojectdlt.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }


}