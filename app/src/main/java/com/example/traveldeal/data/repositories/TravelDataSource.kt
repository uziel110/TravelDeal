package com.example.traveldeal.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveldeal.data.entities.Travel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * fire base com.example.traveldeal.data source
 */
class TravelDataSource {
    private val rootNode = FirebaseDatabase.getInstance()
    private val reference = rootNode.getReference("travels")
    private val liveData: MutableLiveData<Boolean> = MutableLiveData()
    private var uid: String = FirebaseAuth.getInstance().uid.toString()
    private var travelRefChildEventListener: ChildEventListener? = null
    val travelsList: MutableList<Travel> = mutableListOf()
    private var travels: MutableLiveData<MutableList<Travel>> = MutableLiveData()
    private var aTravel: MutableLiveData<Travel> = MutableLiveData()

    fun insert(travel: Travel) {
        val curKey = reference.child(uid).push().key
        if (curKey == null) {
            Log.w(TAG, "Couldn't get push key for travels")
            return
        }
        travel.clientId = curKey
        reference.child(uid).child(curKey).setValue(travel).addOnSuccessListener() {
            liveData.value = true
        }.addOnFailureListener() {
            liveData.value = false
        }
    }

    fun getLiveData(): LiveData<Boolean> {
        return liveData
    }

    fun getAllTravels(): MutableLiveData<MutableList<Travel>> {
/*
        reference.child(uid).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val travel: Travel? = dataSnapshot.getValue(Travel::class.java)
                //val id = dataSnapshot.key
                //travel.setId(id!!.toLong())
                if (travel != null) {
                    travelsList.add(travel)
                }
                //notifyDataChange.OnDataChanged(travelsList)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
*/
        reference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                travelsList.clear()
                for (travelSnapshot in dataSnapshot.children) {
                    val travel: Travel? = travelSnapshot.getValue(Travel::class.java)
                    if (travel != null /*&& travel.requestStatus != resources.getStringArray(R.array.status_array)[3]*/) {
                        travelsList.add(travel)
                    }
                }
                travels.value = travelsList
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return travels
    }

    fun getTravel(travelId: String): MutableLiveData<Travel> {
        lateinit var currTravel: Travel
        reference.child(uid).child(travelId).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val travel: Travel? = dataSnapshot.getValue(Travel::class.java)
                //currTravel = dataSnapshot.getValue(Travel::class.java)!!
                aTravel.value = travel
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return aTravel
    }
}