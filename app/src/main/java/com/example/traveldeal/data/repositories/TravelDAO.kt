package com.example.traveldeal.data.repositories

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.traveldeal.data.entities.Travel

@Dao
interface TravelDAO {

    @Insert
    suspend fun insert(travel: Travel)

    @Update
    fun update(travel: Travel)

    @Delete
    open fun delete(travel: Travel): Unit

    @Query("SELECT * from travels WHERE clientId = :key")
    fun getTravelById(key: String?): LiveData<Travel?>?

    @Query("SELECT * FROM travels")
    fun getTravels(): LiveData<List<Travel>>

    @Query("DELETE FROM travels")
    fun deleteAll()
}
