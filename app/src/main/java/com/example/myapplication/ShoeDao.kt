package com.example.myapplication

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoeDao {

    @Upsert
    suspend fun upsertShoe(shoe: Shoe)

    @Delete
    suspend fun deleteShoe(shoe: Shoe)

    @Query("SELECT * FROM shoe ORDER BY shoeName ASC")
    fun getShoesOrderedByShoeName(): Flow<List<Shoe>>

    @Query("SELECT * FROM shoe ORDER BY shoeType ASC")
    fun getShoesOrderedByShoeType(): Flow<List<Shoe>>

    @Query("SELECT * FROM shoe ORDER BY imageID ASC")
    fun getShoesOrderedByShoeImageID(): Flow<List<Shoe>>

}