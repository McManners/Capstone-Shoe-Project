package com.example.myapplication


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Shoe::class],
    version = 1
)
abstract class ShoeDatabase: RoomDatabase() {

    abstract val dao: ShoeDao
}