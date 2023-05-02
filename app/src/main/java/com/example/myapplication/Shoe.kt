package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shoe(
    val shoeName: String,
    val shoeType: String,
    val imageID: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
