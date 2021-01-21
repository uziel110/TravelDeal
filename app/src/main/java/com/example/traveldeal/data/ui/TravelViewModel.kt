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
    private var rp: TravelRepository = TravelRepository.getTravelRepository(App.instance)
    private var travelsList: MutableLiveData<List<Travel?>?> = MutableLiveData(listOf())

    init {
        //get from the repository only the travels with certain statuses
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
     * delete travel
     * @param travel
     */
    fun deleteItem(travel: Travel) {
        rp.delete(travel)
    }

    /**
     * get liveData
     * @return LiveData<Boolean>
     */
    fun getLiveData(): LiveData<Boolean> {
        return rp.getLiveData()
    }

    /**
     * get all travels from room database with certain statuses
     * @return MutableLiveData list of travels
     */
    fun getTravelsByStatus(): MutableLiveData<List<Travel?>?> {
       return travelsList
    }
}