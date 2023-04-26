package com.example.finalprojectdlt

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.finalprojectdlt.databinding.FragmentShoesUserBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShoesUserFragment : Fragment {

    private lateinit var binding: FragmentShoesUserBinding

    public companion object{
        private const val TAG = "SHOES_USER_TAG"

        public fun newInstance(categoryID: String, category: String, uid: String ): ShoesUserFragment{
            val fragment = ShoesUserFragment()
            val args = Bundle()
            args.putString("categoryID",categoryID)
            args.putString("category",category)
            args.putString("uid",uid)
            fragment.arguments = args
            return fragment
        }
    }
    
    private var categoryID = ""
    private var category = ""
    private var uid = ""

    private lateinit var picArrayList: ArrayList<ModelShoesInfo>
    private lateinit var adapterShoeUser: AdapterShoeUser

    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments
        if(args != null){
            categoryID = args.getString("categoryID")!!
            categoryID = args.getString("category")!!
            categoryID = args.getString("uid")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShoesUserBinding.inflate(LayoutInflater.from(context),container, false)

        Log.d(TAG, "onCreateView: Category: $category")
        if(category == "All"){
            loadAllShoes()

        }
        else{
            loadCategorizedShoes()

        }

        binding.searchEt.addTextChangedListener{object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try{
                    adapterShoeUser.filter.filter(s)
                }
                catch (e: Exception){
                    Log.d(TAG, "onTextChanged: SEARCH RETURNED ${e.message}")
                    
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }}

        return binding.root
    }

    private fun loadAllShoes() {
        picArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Shoes")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            picArrayList.clear()
            for(ds in snapshot.children){
                val model = ds.getValue(ModelShoesInfo::class.java)
                picArrayList.add(model!!)
            }
            adapterShoeUser = AdapterShoeUser(context!!,picArrayList)
                binding.shoesRv.adapter = adapterShoeUser
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun loadCategorizedShoes() {

        picArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Shoes")
        ref.orderByChild("categoryID").equalTo(categoryID)
            .addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                picArrayList.clear()
                for(ds in snapshot.children){
                    val model = ds.getValue(ModelShoesInfo::class.java)
                    picArrayList.add(model!!)
                }
                adapterShoeUser = AdapterShoeUser(context!!,picArrayList)
                binding.shoesRv.adapter = adapterShoeUser
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

}