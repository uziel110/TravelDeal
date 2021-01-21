package com.example.traveldeal.data.repositories

import androidx.lifecycle.LiveData
import com.example.traveldeal.data.entities.Travel

/**
 * interface of remote database
 */
interface ITravelDataSource {

    /**
     * add travel to firebase
     * @param p travel
     */
    fun addTravel(p: Travel)

    /**
     * update travel in firebase
     * @param p travel
     */
    fun updateTravel(p: Travel)

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