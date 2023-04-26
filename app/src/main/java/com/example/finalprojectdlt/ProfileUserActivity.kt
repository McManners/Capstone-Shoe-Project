package com.example.finalprojectdlt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.finalprojectdlt.databinding.ActivityProfileUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserBinding


    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var shoesArrayList: ArrayList<ModelShoesInfo>
    private lateinit var adapterShoeWishlist: AdapterShoeWishlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        loadProfileData()
        loadWishlistShoe()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
//            checkUser()

        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }


    }

    private fun loadProfileData() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("name").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val uid = "${snapshot.child("uid").value}"

                    val dateProfileData = MyApplication.formatTimeStamp(timestamp.toLong())
                    binding.nameTv.text = name
                    binding.joinedDateTv.text = dateProfileData


                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun loadWishlistShoe() {
        shoesArrayList = ArrayList();
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!).child("WishlistContainer")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    shoesArrayList.clear()
                    for(ds in snapshot.children){
                        val shoeId = "${ds.child("shoeId").value}"
                        val modelShoesInfo = ModelShoesInfo()
                        modelShoesInfo.id = shoeId
                        shoesArrayList.add(modelShoesInfo)
                    }
                    adapterShoeWishlist = AdapterShoeWishlist(this@ProfileUserActivity,shoesArrayList)
                    binding.wishlistRv.adapter = adapterShoeWishlist
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

    }
}