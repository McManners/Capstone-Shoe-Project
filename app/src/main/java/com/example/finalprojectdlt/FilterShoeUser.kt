package com.example.finalprojectdlt

import android.widget.Filter

class FilterShoeUser: Filter {

    var filterList: ArrayList<ModelShoesInfo>

    var adapterShoeUser: AdapterShoeUser

    constructor(filterList: ArrayList<ModelShoesInfo>, adapterShoeUser: AdapterShoeUser) : super() {
        this.filterList = filterList
        this.adapterShoeUser = adapterShoeUser
    }

    override fun performFiltering(constraint: CharSequence): FilterResults {
        var constraint: CharSequence? = constraint
        val results = FilterResults()

        if(constraint != null && constraint.isNotEmpty()){
            constraint = constraint.toString().uppercase()
            val filteredModels = ArrayList<ModelShoesInfo>()
            for(i in filterList.indices){
                if(filterList[i].name.uppercase().contains(constraint)){
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values = filteredModels
        }
        else{
            results.count = filterList.size
            results.values = filterList
        }
        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        adapterShoeUser.picArrayList = results.values as ArrayList<ModelShoesInfo>

        adapterShoeUser.notifyDataSetChanged()
    }


}