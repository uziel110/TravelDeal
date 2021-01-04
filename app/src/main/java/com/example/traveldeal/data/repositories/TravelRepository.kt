package com.example.traveldeal.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveldeal.data.entities.Travel


class TravelRepository : Application() {
    lateinit var travels: MutableLiveData<MutableList<Travel>>

    var remoteDataSource: TravelDataSource = TravelDataSource()

    fun insert (travel : Travel){
        remoteDataSource.insert(travel)
    }

    fun getLiveData(): LiveData<Boolean>{
        return remoteDataSource.getLiveData()
    }

    fun getAllTravels(): MutableLiveData<MutableList<Travel>> {
        return remoteDataSource.getAllTravels()
    }
}
