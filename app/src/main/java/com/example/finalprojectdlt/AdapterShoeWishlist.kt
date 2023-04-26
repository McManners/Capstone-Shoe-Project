package com.example.finalprojectdlt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdlt.databinding.RowShoesWishlistBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdapterShoeWishlist : RecyclerView.Adapter<AdapterShoeWishlist.HolderShoeWishlist>{

    private val context: Context

    private var shoesArrayList: ArrayList<ModelShoesInfo>

    private lateinit var binding: RowShoesWishlistBinding

    constructor(context: Context, shoesArrayList: ArrayList<ModelShoesInfo>) {
        this.context = context
        this.shoesArrayList = shoesArrayList
    }
    inner class HolderShoeWishlist(itemView: View) : RecyclerView.ViewHolder(itemView){
        var pdfView = binding.pdfView
        var progressBar = binding.progressBar
        var nameTv = binding.nameTv
        var descTv = binding.descTv
        var categoryTv = binding.categoryTv

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderShoeWishlist {
        binding = RowShoesWishlistBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderShoeWishlist(binding.root)
    }

    override fun getItemCount(): Int {
        return shoesArrayList.size
    }

    override fun onBindViewHolder(holder: HolderShoeWishlist, position: Int) {
        val modelWL = shoesArrayList[position]
        loadShoeDetails(modelWL, holder)
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ShoeDetailActivity::class.java)
            intent.putExtra("shoeId",modelWL.id)
            context.startActivity(intent)
        }

    }

    private fun loadShoeDetails(modelWL: ModelShoesInfo, holder: AdapterShoeWishlist.HolderShoeWishlist) {
        val shoeId = modelWL.id

        val ref = FirebaseDatabase.getInstance().getReference("Shoes")
        ref.child(shoeId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val categoryID = "${snapshot.child("categoryID").value}"
                    val description = "${snapshot.child("description").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val name = "${snapshot.child("name").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val url = "${snapshot.child("url").value}"

                    modelWL.inWishlist = true
                    modelWL.name = name
                    modelWL.description = description
                    modelWL.categoryID = categoryID
                    modelWL.timestamp = timestamp.toLong()
                    modelWL.uid = uid
                    modelWL.url = url

                    MyApplication.loadCategory("$categoryID",holder.categoryTv)
                    MyApplication.loadPicFromUrlSinglePage("$url","$name",holder.pdfView,holder.progressBar,null)

                    holder.nameTv.text = name
                    holder.descTv.text = description

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }
}