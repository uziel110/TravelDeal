package com.example.traveldeal.data.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traveldeal.data.entities.Travel
import utils.Status
import com.example.traveldeal.data.repositories.TravelRepository
import utils.App

/**
 * View model
 */
class TravelViewModel : ViewModel() {
    val app = App
    private var rp: TravelRepository = TravelRepository(app.instance)
    //var travels = MutableLiveData<List<Travel>>()
    var travelsList: MutableLiveData<List<Travel?>?> = MutableLiveData(listOf())
   // var filteredList: MutableLiveData<List<Travel?>?>? = MutableLiveData(listOf())

    init {
        //get from the riposotory only the travels with certain statuses
        rp.getTravelsByStatus(listOf(Status.SENT.ordinal, Status.RECEIVED.ordinal, Status.RUNNING.ordinal))
            .observeForever {
                travelsList.postValue(it)
        }
    }

    /**
     * insert travel
     * @param travel
     */
    fun insertItem(travel: Travel) {
        rp.insert(travel)
    }

    /**
     * update travel
     * @param travel
     */
    fun updateItem(travel: Travel) {
        rp.update(travel)
    }

    /**
     * get liveData
     * @return LiveData<Boolean>
     */
    fun getLiveData(): LiveData<Boolean> {
        return rp.getLiveData()
    }

    /**
     * get all travels
     * @return MutableLiveData list of travels
     */
    fun getAllTravels(): MutableLiveData<List<Travel?>?> {
        return rp.getAllTravels()
    }

    /**
     * get all travels from room database with certain statuses
     * @return MutableLiveData list of travels
     */
    fun getTravelsByStatus():  MutableLiveData<List<Travel?>?>? {
       return travelsList
    }
}