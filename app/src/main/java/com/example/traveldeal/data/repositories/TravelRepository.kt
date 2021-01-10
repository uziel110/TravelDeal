package com.example.traveldeal.data.repositories

import  android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveldeal.data.entities.Travel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class TravelRepository(application: Application) : Application() {
    //lateinit var travels: MutableLiveData<MutableList<Travel>>

    private val remoteDatabase: TravelDataSource = TravelDataSource()
   private val localDatabase = LocalDatabase(application.applicationContext)


//    companion object {
//
//        @Volatile
//        private var INSTANCE: TravelRepository? = null
//
//        fun getInstance(application: Application): TravelRepository =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: TravelRepository(application).also { INSTANCE = it }
//            }
//    }

    //var localDatabase: LocalDatabase = LoacalDatabase(application.applicationContext)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert (travel : Travel){
        //remoteDatabase.insert(travel)
       localDatabase.addTravel(travel)

    }

    fun getLiveData(): LiveData<Boolean>{
        return remoteDatabase.getLiveData()
    }

    fun getAllTravels(): MutableLiveData<MutableList<Travel>> {
        return remoteDatabase.getAllTravels()
    }
    fun getTravel(id: String): MutableLiveData<Travel> {
        return remoteDatabase.getTravel(id)
    }
}
