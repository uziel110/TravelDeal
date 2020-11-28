package data.repositories

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import data.entities.Travel


class TravelDataSource {

    private val rootNode = FirebaseDatabase.getInstance()
    private val reference = rootNode.getReference("travels")

    fun insert(travel: Travel) {
        reference.push().setValue(travel)
            .addOnSuccessListener { Log.d("FirebaseManager", "Upload Successful") }
    }
}