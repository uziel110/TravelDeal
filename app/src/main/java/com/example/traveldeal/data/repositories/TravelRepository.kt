package com.example.traveldeal.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.example.traveldeal.data.entities.Travel


class TravelRepository : Application() {

    var remoteDataSource: TravelDataSource = TravelDataSource()

    fun insert (travel : Travel){
        remoteDataSource.insert(travel)
    }

    fun getLiveData(): LiveData<Boolean>{
        return remoteDataSource.getLiveData()
    }
}
