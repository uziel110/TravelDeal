package com.example.traveldeal.data.repositories

import androidx.lifecycle.LiveData
import com.example.traveldeal.data.entities.Travel

interface ITravelDataSource {
    fun addTravel(travel: Travel)
    //fun addTravels(travelList: List<Travel>)
    fun updateTravel(travel: Travel)
    fun getAllTravels(): MutableList<Travel>
    fun getLiveData(): LiveData<Boolean>

    interface NotifyLiveData{
        fun onDataChange ()
    }
    fun setNotifyLiveData(obj : NotifyLiveData)
}