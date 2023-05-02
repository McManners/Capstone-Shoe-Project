package com.example.finalprojectdlt

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.finalprojectdlt.databinding.ActivityDashboardUserBinding
import com.google.android.material.appbar.AppBarLayout.Behavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var adapterCategory: AdapterCategory

    private lateinit var adapterBrand: AdapterBrand

    private lateinit var categoryArrayList: ArrayList<ModelCategory>

    private lateinit var brandArrayList: ArrayList<ModelBrand>
//    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        loadCategories()
        loadBrands()

        binding.searchEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try{
                    adapterCategory.filter.filter(s)

                }
                catch (e: Exception){
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.tryonBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }


        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()

        }

        binding.accountBtn.setOnClickListener {
            startActivity(Intent(this,ProfileUserActivity::class.java))
        }


        binding.subTitleTv.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()

//            setupWithAdapter(binding.viewPager)
//            binding.tabLayout.setupWithViewPager(binding.viewPager)
        }
    }

    private fun loadBrands() {
        brandArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Brands")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                brandArrayList.clear()
                for(ds in snapshot.children){
                    var model = ds.getValue(ModelBrand::class.java)

                    brandArrayList.add(model!!)
                }
                adapterBrand = AdapterBrand(this@DashboardUserActivity,brandArrayList)
                binding.brandsRv.adapter = adapterBrand

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


    private fun loadCategories() {
        categoryArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryArrayList.clear()
                for(ds in snapshot.children){
                    var model = ds.getValue(ModelCategory::class.java)

                    categoryArrayList.add(model!!)
                }
                adapterCategory = AdapterCategory(this@DashboardUserActivity,categoryArrayList)
                binding.categoriesRv.adapter = adapterCategory

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else {
            val email = firebaseUser.email
            binding.subTitleTv.text = email
        }
    }
}