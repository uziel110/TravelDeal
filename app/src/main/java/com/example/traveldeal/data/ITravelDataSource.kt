package com.example.traveldeal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveldeal.data.entities.Travel

interface ITravelDataSource {
    fun addTravel(p: Travel)
    //fun addTravels(travelList: List<Travel>)
    fun editTravel(p: Travel)
    fun getAllTravels(): MutableList<Travel>
    fun getLiveData(): LiveData<Boolean>

    interface NotifyLiveData{
        fun onDataChange ()
    }
    fun setNotifyLiveData(obj : NotifyLiveData)
}