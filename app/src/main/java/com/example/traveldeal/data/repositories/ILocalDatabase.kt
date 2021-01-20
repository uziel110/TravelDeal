package com.example.traveldeal.data.repositories

import androidx.lifecycle.LiveData
import com.example.traveldeal.data.entities.Travel

/**
 * interface of local database
 */
interface ILocalDatabase {

    /**
     * add travel to room database
     * @param p travel
     */
    fun addTravel(p: Travel)

    /**
     * add travels to room database
     * @param travelList list of travels
     */
    fun addTravels(travelList: List<Travel?>?)

    /**
     * edit travel in room database
     * @param p travel
     */
    fun editTravel(p: Travel)

    /**
     * delete travel from room database
     * @param p travel
     */
    fun deleteTravel(p: Travel)

    /**
     * get all travels from room database
     * @return LiveData list of travels
     */
    fun getAllTravels(): LiveData<List<Travel>>

    /**
     * get all travels from room database with certain statuses
     * @param status list of int which represent travels statuses
     * @return LiveData list of travels
     */
    fun getTravelsByStatus(status :List<Int>): LiveData<List<Travel>>

    /**
     * clear table
     */
    fun clearTable()
}