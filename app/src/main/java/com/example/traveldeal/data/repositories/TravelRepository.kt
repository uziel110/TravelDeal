package com.example.traveldeal.data.repositories

import  android.app.Application
import android.app.Service
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveldeal.data.entities.Travel

class TravelRepository(context: Context) : Application() {

    private var remoteDatabase: ITravelDataSource = TravelDataSource()
    private val localDatabase = LocalDatabase(context)
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
                    val tempList = remoteDatabase.getAllTravels()
                    travelsList.postValue(tempList)
                    localDatabase.clearTable()
                    localDatabase.addTravels(tempList)
                }
            }
        remoteDatabase.setNotifyLiveData(notifyData)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(travel: Travel) {
        remoteDatabase.addTravel(travel)
    }

    fun getLiveData(): LiveData<Boolean> {
        return remoteDatabase.getLiveData()
    }

    fun getAllTravels(): MutableLiveData<List<Travel?>?> {
        return travelsList
    }

    fun getTravelsByStatus(status: List<Int>): LiveData<List<Travel>> {
        return localDatabase.getTravelsByStatus(status)
    }

    fun update(travel: Travel) {
        remoteDatabase.updateTravel(travel)
    }
}
