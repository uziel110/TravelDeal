package com.example.traveldeal.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.traveldeal.data.entities.Travel
import java.util.concurrent.Executors

@Database(entities = [Travel::class], version = 1, exportSchema = false)
abstract class TravelRoomDatabase : RoomDatabase() {

    abstract fun travelDAO(): TravelDAO

    // marking the instance as volatile to ensure atomic access to the variable
    @Volatile
    private lateinit var INSTANCE: TravelRoomDatabase
    private val NUMBER_OF_THREADS = 4
    val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

    open fun getDatabase(context: Context): TravelRoomDatabase {
        if (INSTANCE == null) {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TravelRoomDatabase::class.java,
                        "Travel database"
                    ).addCallback(sRoomDatabaseCallback).build()
                }
            }
        }
        return INSTANCE
    }

    private val sRoomDatabaseCallback: Callback = object : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
//            this@TravelRoomDatabase.databaseWriteExecutor.execute(
//                Runnable { // Populate the database in the background.
//                    // If you want to start with more words, just add them.
//                    var dao: TravelDAO = INSTANCE!!.travelDAO()
//                    dao.deleteAll()
//
//
//                    lateinit var travel: Travel
//                    travel.clientName = "xx"
//                    travel.clientPhone = "xx"
//                    travel.clientEmailAddress = "xx"
//                    travel.departureAddress = "xx"
//                    travel.departureDate = "xx"
//                    travel.destinationAddress = "xx"
//                    travel.returnDate = "xx"
//                    travel.passengersNumber = 0
//                    travel.requestStatus = "xx"
//                    dao.Insert(travel)
//                })
        }
    }
}