package com.example.traveldeal.data.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.traveldeal.data.entities.Travel
import com.example.traveldeal.data.repositories.TravelRepository

class TravelViewModel(app: Application) : AndroidViewModel(app) {
    private var rp: TravelRepository = TravelRepository()

    fun insertItem(travel: Travel){
        rp.insert(travel)
    }

    fun getLiveData(): LiveData<Boolean> {
        return rp.getLiveData()
    }
}