package ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.entities.Travel
import data.repositories.TravelRepository

class TravelViewModel(app: Application) : AndroidViewModel(app) {


   var rp: TravelRepository
//    var r: Context = getApplication()

    init {
        rp = TravelRepository()
    }

    fun insertItem(travel: Travel) {
       rp.insert(travel)
    }

//    fun getItems(): LiveData<Travel> {
//        return rp.getTravrls()
//    }
}