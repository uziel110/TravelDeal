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
class TravelRepository private constructor(context: Context) : Application() {

    private var remoteDatabase: ITravelDataSource = TravelDataSource.instance
    private val localDatabase = LocalDatabase.getLocalDatabase(context)
    val travelsList = MutableLiveData<List<Travel?>?>()

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: TravelRepository? = null

        fun getTravelRepository(context: Context): TravelRepository {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = TravelRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

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
     * @param status list of int which represent travels statuses
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

    /**
     * update travel in firebase
     * @param travel
     */
    fun delete(travel: Travel) {
        remoteDatabase.deleteTravel(travel)
    }
}
