package com.example.traveldeal.data.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveldeal.data.entities.Travel
import com.example.traveldeal.data.repositories.TravelRepository

class TravelViewModel(app: Application) : AndroidViewModel(app) {
    private var rp: TravelRepository = TravelRepository()
    //var travels = MutableLiveData<List<Travel>>()

    fun insertItem(travel: Travel) {
        rp.insert(travel)
    }

    fun getLiveData(): LiveData<Boolean> {
        return rp.getLiveData()
    }

    fun getAllTravels(): MutableLiveData<MutableList<Travel>> {
        return rp.getAllTravels()
    }

    fun getTravel(id: String): MutableLiveData<Travel> {
        return rp.getTravel(id)
    }
}