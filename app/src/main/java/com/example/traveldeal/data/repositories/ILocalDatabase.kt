package com.example.traveldeal.data.repositories

import androidx.lifecycle.LiveData
import com.example.traveldeal.data.entities.Travel

interface ILocalDatabase {
    suspend fun addTravel(p: Travel)
    //fun addTravels(travelList: List<Travel>)
    fun editTravel(p: Travel)
    fun deleteTravel(p: Travel)
    fun getAllTravels(): LiveData<List<Travel>>
    fun clearTable()
}