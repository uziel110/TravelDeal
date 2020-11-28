package data.repositories

import androidx.lifecycle.LiveData
import androidx.room.*
import data.entities.Travel

@Dao
interface TravelDAO {

    @Insert
    fun Insert(travel: Travel)

    @Update
    fun Update(travel: Travel)

    @Delete
    open fun Delete(travel: Travel): Unit

//    @Query("SELECT * from travels WHERE clientName = :key")
//    fun getItemById(key: String?): Travel?

    @Query("SELECT * FROM travels")
    fun getItems(): Travel

    @Query("DELETE FROM travels")
    fun deleteAll()
}
