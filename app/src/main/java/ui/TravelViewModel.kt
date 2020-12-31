package ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import data.entities.Travel
import data.repositories.TravelRepository

class TravelViewModel(app: Application) : AndroidViewModel(app) {
    private var rp: TravelRepository = TravelRepository()

    fun insertItem(travel: Travel): Task<Void> {
        return rp.insert(travel)
    }
}