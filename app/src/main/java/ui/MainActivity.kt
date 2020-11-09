package ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.traveldeal.R
import com.google.firebase.database.FirebaseDatabase

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when the user taps the Send button */
    fun startTravelButton(view: View) {
        val message = "I want to see my text"

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        myRef.setValue("Hello, World!")


        database.getReference("new Message").setValue("Hello, new World!")

//        val myRootRef = FirebaseDatabase.getInstance().reference;
//        val cinemasRef = myRootRef.child("message");
//        cinemasRef.setValue("I logged");

//        val intent = Intent(this, AddTravelActivity::class.java).apply {
//            putExtra(EXTRA_MESSAGE, message)
//        }
//        startActivity(intent)
    }
}