package data.repositories

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import data.entities.Travel
import java.util.concurrent.Executors


class TravelRepository : Application() {

    var remoteDataSource: TravelDataSource = TravelDataSource()

    fun insert (travel : Travel) {
        // TODO: 26/11/2020 check for internet connection

        remoteDataSource.insert(travel)

    }
}
