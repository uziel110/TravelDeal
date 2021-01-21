package com.example.traveldeal.data.repositories

import androidx.lifecycle.LiveData
import com.example.traveldeal.data.entities.Travel

/**
 * interface of remote database
 */
interface ITravelDataSource {

    /**
     * add travel to firebase
     * @param travel travel
     */
    fun addTravel(travel: Travel)

    /**
     * update travel in firebase
     * @param travel travel
     */
    fun updateTravel(travel: Travel)

    /**
     * delete travel in firebase
     * @param travel travel
     */
    fun deleteTravel(travel: Travel)

    /**
     * get all travels from firebase
     * @return MutableList of travels
     */
    fun getAllTravels(): MutableList<Travel>

    /**
     * get all travels from firebase
     * @return LiveData list of travels
     */
    fun getLiveData(): LiveData<Boolean>

    interface NotifyLiveData{
        fun onDataChange ()
    }
    fun setNotifyLiveData(obj : NotifyLiveData)
}