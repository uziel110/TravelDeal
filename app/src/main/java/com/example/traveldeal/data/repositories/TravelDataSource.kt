package com.example.traveldeal.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveldeal.data.entities.Travel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * class that implement ITravelDataSource interface
 */
class TravelDataSource private constructor(): ITravelDataSource {

    private object HOLDER {
        val INSTANCE = TravelDataSource()
    }

    companion object {
        val instance: TravelDataSource by lazy { HOLDER.INSTANCE }
    }


    private val rootNode = FirebaseDatabase.getInstance()
    private val reference = rootNode.getReference("travels")
    val liveData: MutableLiveData<Boolean> = MutableLiveData()
    private var uid: String = FirebaseAuth.getInstance().uid.toString()
    var travelsList: MutableList<Travel> = mutableListOf()
    private var travels: MutableLiveData<MutableList<Travel>> = MutableLiveData()
    private var aTravel: MutableLiveData<Travel> = MutableLiveData()

    lateinit var notifyData: ITravelDataSource.NotifyLiveData

    init {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                travelsList.clear()
                for (travelSnapshot in dataSnapshot.children) {
                    val travel: Travel? = travelSnapshot.getValue(Travel::class.java)
                    //add to the list the user's travels
                    if (travel != null && travel.clientId == uid) {
                        travelsList.add(travel)
                    }
                }
                notifyData.onDataChange()
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    override fun addTravel(travel: Travel) {
        val curKey = reference.push().key
        if (curKey == null) {
            Log.w(TAG, "Couldn't get push key for travels")
            return
        }
        travel.travelId = curKey
        reference.child(curKey).setValue(travel).addOnSuccessListener {
            liveData.value = true
        }.addOnFailureListener {
            liveData.value = false
        }
    }

    override fun updateTravel(travel: Travel) {
        val curKey = travel.travelId
        reference.child(curKey).setValue(travel)
    }

    override fun deleteTravel(travel: Travel) {
        reference.child(travel.travelId).removeValue()
    }

    override fun getLiveData(): LiveData<Boolean> {
        return liveData
    }

    override fun getAllTravels(): MutableList<Travel> {
        return travelsList
    }

    override fun setNotifyLiveData(obj: ITravelDataSource.NotifyLiveData) {
        notifyData = obj
    }
}