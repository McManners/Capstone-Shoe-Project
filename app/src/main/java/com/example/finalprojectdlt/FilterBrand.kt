package com.example.finalprojectdlt

import android.widget.Filter

class FilterBrand: Filter {
    private var filterList: ArrayList<ModelBrand>
    private var adapterBrand: AdapterBrand

    constructor(filterList: ArrayList<ModelBrand>, adapterBrand: AdapterBrand) : super() {
        this.filterList = filterList
        this.adapterBrand = adapterBrand
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        if(constraint != null && constraint.isNotEmpty()){

            constraint = constraint.toString().uppercase()
            val filteredModels:ArrayList<ModelBrand> = ArrayList()
            for(i in 0 until filterList.size){
                if (filterList[i].brand.uppercase().contains(constraint)){
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values = filteredModels


        }
        else {
            results.count = filterList.size
            results.values = filterList
        }
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        adapterBrand.brandArrayList = results.values as ArrayList<ModelBrand>
        adapterBrand.notifyDataSetChanged()
    }

}