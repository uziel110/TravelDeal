package com.example.traveldeal.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.example.traveldeal.data.entities.Travel

/**
 * fire base com.example.traveldeal.data source
 */
class TravelDataSource {
    private val rootNode = FirebaseDatabase.getInstance()
    private val reference = rootNode.getReference("travels")
    private val liveData: MutableLiveData<Boolean> = MutableLiveData()

    fun insert(travel: Travel) {
        //val curRef = reference.child(travel.clientEmailAddress)
        reference.push().setValue(travel).addOnSuccessListener() {
            liveData.value = true
        }.addOnFailureListener() {
            liveData.value = false
        }
    }

    fun getLiveData(): LiveData<Boolean>{
        return liveData
    }
}