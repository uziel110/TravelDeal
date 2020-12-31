package data.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import data.entities.Travel

/**
 * fire base data source
 */
class TravelDataSource {
    private val rootNode = FirebaseDatabase.getInstance()
    private val reference = rootNode.getReference("travels")

    fun insert(travel: Travel): Task<Void> {
        //val curRef = reference.child(travel.clientEmailAddress)
        return reference.push().setValue(travel)
    }
}