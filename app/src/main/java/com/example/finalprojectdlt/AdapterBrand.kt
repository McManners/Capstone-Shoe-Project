package com.example.finalprojectdlt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdlt.databinding.RowBrandsBinding

class AdapterBrand: RecyclerView.Adapter<AdapterBrand.HolderBrand>, Filterable {
    private val context: Context
    public var brandArrayList:ArrayList<ModelBrand>
    private var filterList: ArrayList<ModelBrand>

    private var filter: FilterBrand? = null

    private lateinit var binding: RowBrandsBinding

    constructor(
        context: Context,
        brandArrayList: ArrayList<ModelBrand>,
    ) {
        this.context = context
        this.brandArrayList = brandArrayList
        this.filterList = brandArrayList
    }

    inner class HolderBrand(itemView: View): RecyclerView.ViewHolder(itemView){
        var brandsTv: TextView = binding.brandsTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBrand {
        binding = RowBrandsBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderBrand(binding.root)
    }

    override fun getItemCount(): Int {
        return brandArrayList.size
    }

    override fun onBindViewHolder(holder: HolderBrand, position: Int) {
        val model = brandArrayList[position]
        val id = model.id
        val brand = model.brand
        val uid = model.uid
        val timestamp = model.timestamp

        holder.brandsTv.text = brand

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ShoeListAdActivity::class.java)
            intent.putExtra("brandID",id)
            intent.putExtra("brand",brand)
            context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterBrand(filterList,this)
        }
        return filter as FilterBrand
    }

}