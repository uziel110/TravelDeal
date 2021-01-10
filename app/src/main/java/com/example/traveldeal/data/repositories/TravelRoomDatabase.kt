package com.example.traveldeal.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.traveldeal.data.entities.Travel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import utils.UserLocation


@Database(entities = [Travel::class], version = 1)
@TypeConverters(Travel.UserLocationConverter::class)
abstract class TravelRoomDatabase : RoomDatabase() {

    abstract fun travelDao(): TravelDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TravelRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TravelRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelRoomDatabase::class.java,
                    "travel_database"
                )
//                    .fallbackToDestructiveMigration()
//                    .addCallback(TravelDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


//        private class TravelDatabaseCallback(
//            private val scope: CoroutineScope
//        ) : RoomDatabase.Callback() {
//            /**
//             * Override the onCreate method to populate the database.
//             */
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.travelDao())
//                    }
//                }
//            }
//        }
//
//        /**
//         * Populate the database in a new coroutine.
//         * If you want to start with more words, just add them.
//         */
//        suspend fun populateDatabase(travelDao: TravelDAO) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//            // Delete all content here.
//            travelDao.deleteAll()
//
//            var testUL = UserLocation(0.0, 0.0)
//            // Add sample words.
//            //                    var travel = Travel("test",
//            //                        "test",
//            //                        "test",
//            //                        "test",
//            //                        testUL,
//            //                        "test",
//            //                        "test",
//            //                        testUL,
//            //                        "test",
//            //                        "test",
//            //                        "test")
//
//            var travel = Travel()
//            travel.clientId = "1"
//            travel.clientName = "test"
//            travel.clientPhone = "test"
//            travel.clientEmailAddress = "test"
//            travel.departureAddress = "test"
//            travel.departLocation = testUL
//            travel.departureDate = "test"
//            travel.destinationAddress = "test"
//            travel.destLocation = testUL
//            travel.returnDate = "test"
//            travel.passengersNumber = 1
//            travel.requestStatus = "test"
//
//            travelDao.insert(travel)
//        }
    }
}