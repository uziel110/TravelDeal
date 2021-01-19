package com.example.traveldeal.data.repositories

import  android.app.Application
import android.app.Service
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveldeal.data.entities.Travel

/**
 * Travel repository
 */
class TravelRepository(context: Context) : Application() {

    private var remoteDatabase: ITravelDataSource = TravelDataSource()
    private val localDatabase = LocalDatabase(context)
    val travelsList = MutableLiveData<List<Travel?>?>()

    init {
        val notifyData: ITravelDataSource.NotifyLiveData =
            object : ITravelDataSource.NotifyLiveData {
                override fun onDataChange() {
                    //get all travels from firebase
                    val tempList = remoteDatabase.getAllTravels()
                    travelsList.postValue(tempList)
                    //clear room database
                    localDatabase.clearTable()
                    //insert to room database the travels from firebase
                    localDatabase.addTravels(tempList)
                }
            }
        remoteDatabase.setNotifyLiveData(notifyData)
    }

    /**
     * insert travel to firebase
     * @param travel
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(travel: Travel) {
        remoteDatabase.addTravel(travel)
    }

    /**
     * get firebase liveData
     * @return LiveData<Boolean>
     */
    fun getLiveData(): LiveData<Boolean> {
        return remoteDatabase.getLiveData()
    }

    /**
     * get all travels
     * @return MutableLiveData of travels
     */
    fun getAllTravels(): MutableLiveData<List<Travel?>?> {
        return travelsList
    }

    /**
     * get all travels from room database with certain statuses
     * @param list of int which represent travels statuses
     * @return LiveData list of travels
     */
    fun getTravelsByStatus(status: List<Int>): LiveData<List<Travel>> {
        return localDatabase.getTravelsByStatus(status)
    }

    /**
     * update travel in firebase
     * @param travel
     */
    fun update(travel: Travel) {
        remoteDatabase.updateTravel(travel)
    }
}
