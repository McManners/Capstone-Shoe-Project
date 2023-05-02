package com.example.finalprojectdlt

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalprojectdlt.databinding.ActivityNewAddBrandBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NewAddBrandActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewAddBrandBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAddBrandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.submitBtn.setOnClickListener {
            validateData()
        }
    }

    private var brand = ""

    private fun validateData() {
        brand = binding.brandEt.text.toString().trim()

        if(brand.isEmpty()){
            Toast.makeText(this,"Enter Category...", Toast.LENGTH_SHORT).show()

        }
        else {
            addBrandFirebase()
        }

    }

    private fun addBrandFirebase() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["brand"] = brand
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

        val ref = FirebaseDatabase.getInstance().getReference("Brands")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "SUCCESS!", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener { e->
                progressDialog.dismiss()

                Toast.makeText(this,"Failed to add due to ${e.message}",Toast.LENGTH_SHORT).show()

    }
}
}