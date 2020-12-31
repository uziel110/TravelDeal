package data.repositories

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import data.entities.Travel
import java.util.concurrent.Executors


class TravelRepository : Application() {

    var remoteDataSource: TravelDataSource = TravelDataSource()

    fun insert (travel : Travel): Task<Void> {
        return remoteDataSource.insert(travel)
    }
}
