package com.example.roomdatabase314.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="user_data")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val brandName: String
)
