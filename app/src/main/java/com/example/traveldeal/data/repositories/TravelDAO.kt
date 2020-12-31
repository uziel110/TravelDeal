package com.example.traveldeal.data.repositories

import androidx.room.*
import com.example.traveldeal.data.entities.Travel

@Dao
interface TravelDAO {

    @Insert
    fun Insert(travel: Travel)

    @Update
    fun Update(travel: Travel)

    @Delete
    open fun Delete(travel: Travel): Unit

//    @Query("SELECT * from travels WHERE clientName = :key")
//    fun getItemById(key: String?): Travel?

    @Query("SELECT * FROM travels")
    fun getItems(): Travel

    @Query("DELETE FROM travels")
    fun deleteAll()
}
