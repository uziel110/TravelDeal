package com.example.traveldeal.data.repositories

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


    fun insert(travel: Travel) {
        //val curRef = reference.child(travel.clientEmailAddress)
        reference.child(uid).push().setValue(travel).addOnSuccessListener() {
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
                    if (travel != null) {
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

//    fun NotifyToStudentList(notifyDataChange: NotifyDataChange<List<Travel>>) {
//        if (notifyDataChange != null) {
//            if (travelRefChildEventListener != null) {
//                notifyDataChange.onFailure(Exception("first unNotify student list"))
//                return
//            }
//            travelsList.clear()
//            travelRefChildEventListener = object : ChildEventListener {
//                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//                    val travel: Travel? = dataSnapshot.getValue(Travel::class.java)
//                    //val id = dataSnapshot.key
//                    //travel.setId(id!!.toLong())
//                    if (travel != null) {
//                        travelsList.add(travel)
//                    }
//                    notifyDataChange.OnDataChanged(travelsList)
//                }
//
//                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
//                    val student: Travel? = dataSnapshot.getValue(Travel::class.java)
//                    /*
//                    val id = dataSnapshot.key!!.toLong()
//                    student.setId(id)
//                    for (i in 0 until travelsList.size()) {
//                        if (travelsList.get(i).getId().equals(id)) {
//                            travelsList.set(i, student)
//                            break
//                        }
//                    }
//                    */
//                    notifyDataChange.OnDataChanged(travelsList)
//                }
//
//                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                    val student: Travel? = dataSnapshot.getValue(Travel::class.java)
//                    /*
//                    val id = dataSnapshot.key!!.toLong()
//                    travel.setId(id)
//                    for (i in 0 until travelsList.size()) {
//                        if (travelsList.get(i).getId() === id) {
//                            travelsList.remove(i)
//                            break
//                        }
//                    }
//                     */
//                    notifyDataChange.OnDataChanged(travelsList)
//                }
//
//                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
//                override fun onCancelled(databaseError: DatabaseError) {
//                    notifyDataChange.onFailure(databaseError.toException())
//                }
//            }
//            travelsList.addChildEventListener(travelRefChildEventListener)
//        }
//    }
//
//    fun stopNotifyToStudentList() {
//        if (travelRefChildEventListener != null) {
//            travelsList.removeEventListener(travelRefChildEventListener)
//            travelRefChildEventListener = null
//        }
//    }
}