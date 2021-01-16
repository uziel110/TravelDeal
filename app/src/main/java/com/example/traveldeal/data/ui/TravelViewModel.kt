package com.example.traveldeal.data.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traveldeal.data.entities.Travel
import com.example.traveldeal.data.enums.Status
import com.example.traveldeal.data.repositories.TravelRepository
import utils.App

class TravelViewModel : ViewModel() {
    val app = App
    private var rp: TravelRepository = TravelRepository(app.instance)
    //var travels = MutableLiveData<List<Travel>>()
    var travelsList: MutableLiveData<List<Travel?>?> = MutableLiveData(listOf())
   // var filteredList: MutableLiveData<List<Travel?>?>? = MutableLiveData(listOf())

    init {
        rp.getTravelsByStatus(listOf(Status.SENT.ordinal, Status.RECEIVED.ordinal, Status.RUNNING.ordinal))
            .observeForever {
                travelsList.postValue(it)
        }
    }

    fun insertItem(travel: Travel) {
        rp.insert(travel)
    }

    fun getLiveData(): LiveData<Boolean> {
        return rp.getLiveData()
    }

    fun getAllTravels(): MutableLiveData<List<Travel?>?> {
        return rp.getAllTravels()
    }

    fun getTravelsByStatus():  MutableLiveData<List<Travel?>?>? {
       return travelsList
    }

    fun updateItem(travel: Travel) {
        rp.update(travel)
    }

//    fun getTravel(id: String): MutableLiveData<Travel> {
//        return rp.getTravel(id)
//    }
}