package com.example.finalprojectdlt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdlt.databinding.RowShoeinfoUserBinding


class AdapterShoeUser: RecyclerView.Adapter<AdapterShoeUser.HolderShoeUser>, Filterable {

    private var context: Context
    public var picArrayList: ArrayList<ModelShoesInfo>
    public val filterList: ArrayList<ModelShoesInfo>


    private lateinit var binding: RowShoeinfoUserBinding

    private var filter : FilterShoeUser? = null

    constructor(context: Context, picArrayList: ArrayList<ModelShoesInfo>) {
        this.context = context
        this.picArrayList = picArrayList
        this.filterList = picArrayList
    }


    inner class HolderShoeUser(itemView:View): RecyclerView.ViewHolder(itemView){

        var pdfView = binding.pdfView
        var progressBar = binding.progressBar
        var titleTv = binding.titleTv
        var descriptionTv = binding.descriptionTv
        var categoryTv = binding.categoryTv
        var sizeTv = binding.sizeTv
        var dateTv = binding.dateTv

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderShoeUser {
        binding = RowShoeinfoUserBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderShoeUser(binding.root)
    }

    override fun getItemCount(): Int {
        return picArrayList.size
    }

    override fun onBindViewHolder(holder: HolderShoeUser, position: Int) {
        val model = picArrayList[position]
        val shoeId = model.id
        val categoryID = model.categoryID
        val name = model.name
        val description = model.description
        val uid = model.uid
        val url = model.url
        val timestamp = model.timestamp
        val date = MyApplication.formatTimeStamp(timestamp)

        holder.titleTv.text = name
        holder.descriptionTv.text= description
        holder.dateTv.text = date

        MyApplication.loadCategory(categoryID, holder.categoryTv)
        MyApplication.loadPicFromUrlSinglePage(url, name, holder.pdfView , holder.progressBar, null)
        MyApplication.loadShoeInfoSize(url, name, holder.sizeTv)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ShoeDetailActivity::class.java)
            intent.putExtra("shoeId", shoeId)
            context.startActivity(intent)
        }

    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterShoeUser(filterList,this)

        }
        return filter as FilterPicAdmin
    }
}