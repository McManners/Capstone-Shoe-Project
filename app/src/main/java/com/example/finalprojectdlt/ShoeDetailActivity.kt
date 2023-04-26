package com.example.finalprojectdlt

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.finalprojectdlt.databinding.ActivityShoeDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log

class ShoeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoeDetailBinding

    private var shoeId =""

    private var inWishListCart = false



    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shoeId = intent.getStringExtra("shoeId")!!

        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser != null){
            wishlistCart()
        }

        loadShoeDetails()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.wishlistBtn.setOnClickListener {
            if(firebaseAuth.currentUser == null){
                Toast.makeText(this,"Login Error", Toast.LENGTH_SHORT).show()
            }
            else {
                if(inWishListCart){
                    removeWishlist()
                }
                else{
                    addToWishlist()
                }

            }

        }




    }

    private fun loadShoeDetails() {
        val ref = FirebaseDatabase.getInstance().getReference("Shoes")
        ref.child(shoeId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val categoryID = "${snapshot.child("categoryID").value}"
                    val description =  "${snapshot.child("description").value}"
                    val name =  "${snapshot.child("name").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val url = "${snapshot.child("url").value}"

                    MyApplication.loadCategory(categoryID, binding.categoryTv)
                    MyApplication.loadPicFromUrlSinglePage("$url","$name",binding.picView,binding.progressBar,binding.pagesTv)
                    binding.titleTv.text=name
                    binding.descriptionTv.text=description

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
    private fun wishlistCart(){
        Log.d(TAG, "wishlistCart: Checking cart...")

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!).child("WishlistContainer").child(shoeId)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    inWishListCart = snapshot.exists()
                    if(inWishListCart){
                        Log.d(TAG, "onDataChange: This will make it available to wishlist")
                        binding.wishlistBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_addtowishlist,0,0)
                        binding.wishlistBtn.text = "Remove from wishlist"
                    }
                    else{
                        Log.d(TAG, "onDataChange: This will make it available to de-list in wishlist ")
                        binding.wishlistBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_wishlisted,0,0)
                        binding.wishlistBtn.text = "Add to wishlist"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

    }

    private fun addToWishlist(){
        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["shoeId"] = shoeId
        hashMap["timestamp"] = timestamp

        Log.d(TAG, "addToWishlist: Adding to Wishlist... ")
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!).child("WishlistContainer").child(shoeId)
            .setValue(hashMap)
            .addOnSuccessListener {
                Log.d(TAG, "addToWishlist: Successfully added to wishlist")
                Toast.makeText(this, "Successfully added to wishlist", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "addToWishlist: Failed to add to wishlist ${e.message}")
            }
    }
    private fun removeWishlist(){


        Log.d(TAG, "removeWishlist: Removing from Wishlist...")

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!).child("WishlistContainer").child(shoeId)
            .removeValue().addOnSuccessListener {
                Log.d(TAG, "removeWishlist: Removed from Wishlist...")
                Toast.makeText(this, "Removed from wishlist!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Log.d(TAG, "removeWishlist: Error Message: ${e.message}")
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()

            }

    }
//
//    private fun loadShoeDetails() {
//        val ref = FirebaseDatabase.getInstance().getReference("Shoes")
//        ref.child(id)
//            .addListenerForSingleValueEvent(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val categoryID = "${snapshot.child(" categoryID ").value}"
//                    val description =  "${snapshot.child(" description ").value}"
//                    val name =  "${snapshot.child(" name ").value}"
//                    val timestamp = "${snapshot.child(" timestamp ").value}"
//                    val uid = "${snapshot.child(" uid ").value}"
//                    val url = "${snapshot.child(" url ").value}"
//
//                    MyApplication.loadCategory(categoryID,binding.categoryTv)
//
//                    MyApplication.loadPicFromUrlSinglePage("$url","$name", binding.picView, binding.progressBar, binding.pagesTv)
//
//                    binding.titleTv.text= name
//                    binding.descriptionTv.text= description
//
//
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                }
//            })
//    }
}