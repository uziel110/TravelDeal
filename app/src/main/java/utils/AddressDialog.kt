package utils

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.traveldeal.R
import com.example.traveldeal.data.ui.AddTravelActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlin.properties.Delegates

class AddressDialog : AppCompatActivity() {
    lateinit var bSave: Button
    private var isSourceAddress by Delegates.notNull<Boolean>()
    private lateinit var currentPlace: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isSourceAddress = intent.getBooleanExtra("bool", false)
//        setContentView(R.layout.address_form)
//        bSave = findViewById(R.id.bSaveAddress)


//        bSave.setOnClickListener {
//
//            if (isSourceAddress)
//                AddTravelActivity.sourceAddress = currentPlace
//            else {
//                AddTravelActivity.addressMutableList.add(currentPlace)
//                AddTravelActivity.address.notifyDataSetChanged()
//            }
//            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
//            this.finish()
//
//        }


    }
}