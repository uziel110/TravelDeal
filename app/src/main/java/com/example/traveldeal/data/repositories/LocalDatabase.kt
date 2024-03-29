package com.example.traveldeal.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.traveldeal.data.entities.Travel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * class that implement ILocalDatabase interface
 */
class LocalDatabase private constructor(context: Context) : ILocalDatabase {


    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getLocalDatabase(context: Context): LocalDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE?: synchronized(this) {
                val instance = LocalDatabase(context)
                INSTANCE = instance
                instance
            }
        }
    }
    private val database = TravelRoomDatabase.getDatabase(context)
    var travelDao = database.travelDao()


    override fun addTravel(p: Travel) {
        travelDao.insert(p)
    }

    override fun addTravels(travelList: List<Travel?>?) {
        travelDao.insertList(travelList)
    }

    override fun editTravel(p: Travel) {
        travelDao.update(p)
    }

    override fun deleteTravel(p: Travel) {
        travelDao.delete(p)
    }

    override fun getAllTravels(): LiveData<List<Travel>> {
        return travelDao.getTravels()
    }

    override fun getTravelsByStatus(status :List<Int>): LiveData<List<Travel>> {
        return travelDao.getTravelsByStatus(status)
    }

    override fun clearTable() {
        travelDao.deleteAll()
    }
}