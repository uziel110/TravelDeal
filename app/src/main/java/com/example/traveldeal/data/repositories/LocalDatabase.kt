package com.example.traveldeal.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.traveldeal.data.entities.Travel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LocalDatabase(context: Context): ILocalDatabase{
    private var travelDao: TravelDAO

    init {
        val database = TravelRoomDatabase.getDatabase(context, CoroutineScope(SupervisorJob()))
        travelDao = database.travelDao()
        //  travelDao.clear()
    }

    fun getTravel(id: String?): LiveData<Travel?>? {
        return travelDao.getTravelById(id)
    }

    override suspend fun addTravel(p: Travel) {
        travelDao.insert(p)
    }

    override fun editTravel(p: Travel) {
        travelDao.update(p)
    }

    override fun deleteTravel(p: Travel) {
        travelDao.delete(p)
    }

    override fun getAllTravels(): LiveData<List<Travel>> {
        val l= travelDao.getTravels().observeForever{it -> Log.i("test",it.size.toString())}
        return travelDao.getTravels()
    }

    override fun clearTable() {
        travelDao.deleteAll()
    }
}