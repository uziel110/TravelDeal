package data.repositories

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import data.entities.Travel
import java.util.concurrent.Executors


class TravelRepository : Application {

    lateinit var remoteDataSource: TravelDataSource
    //lateinit var roomDatabase : TravelRoomDatabase
    // lateinit var travelDAO: TravelDAO

    private val NUMBER_OF_THREADS = 4
    val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

    constructor() {
        remoteDataSource = TravelDataSource()
//        roomDatabase = roomDatabase.getDatabase(app)
//        travelDAO = roomDatabase.travelDAO()
    }

    fun insert (travel : Travel) {

        // TODO: 26/11/2020 check for internt conection
        remoteDataSource.insert(travel)
        
//        databaseWriteExecutor.execute( Runnable(){
//            fun run(){
//                travelDAO.Insert(travel)
//            }
//        })
    }

//    fun getTravrls(): LiveData<Travel> {
//        remoteDataSource.getTravrls()
//    }
}
