package data.repositories

import com.google.firebase.database.*
import data.entities.Travel


class TravelDataSource {

    val rootNode = FirebaseDatabase.getInstance()
    val reference = rootNode.getReference("travels")

    fun insert(travel: Travel) {

        //        reference.child(travel.travelKey.toString()).setValue(travel)
//        val key = reference.child("Key").getValue()
        reference.push().setValue(travel,DatabaseReference.CompletionListener(){
                error:DatabaseError?,ref->System.err.println("Value was set. Error = $error")
        })


        //reference.child(travel.travelKey.toString()).setValue(travel)

//
//        getReference.child("DataInputManual").child(key).child("id_data").setValue(key)
//            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    //Do what you need to do
//                }
//            });

    }




}