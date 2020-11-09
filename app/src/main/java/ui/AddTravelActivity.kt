package ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.traveldeal.R
import com.google.firebase.database.FirebaseDatabase
import entities.Travel


class AddTravelActivity : AppCompatActivity() {

    lateinit var saveButton: Button
    lateinit var etClientName: EditText
    lateinit var etPhone: EditText
    lateinit var etEmailAddress: EditText
    lateinit var etDepartureDate: EditText
    lateinit var etReturnDate: EditText
    lateinit var etPassengersNumber: EditText
    lateinit var etDepartureAddress: EditText
    lateinit var etDestinationAddress: EditText
    lateinit var spinnerRequestStatus: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_travel)

        saveButton = findViewById(R.id.buttonSave)
        etClientName = findViewById(R.id.editTextClientName)
        etPhone = findViewById(R.id.editTextPhone)
        etEmailAddress = findViewById(R.id.editTextEmailAddress)
        etDepartureDate = findViewById(R.id.editTextDepartureDate)
        etReturnDate = findViewById(R.id.editTextReturnDate)
        etPassengersNumber = findViewById(R.id.editTextPassengersNumber)
        etDepartureAddress = findViewById(R.id.editTextTextDepartureAddress)
        etDestinationAddress = findViewById(R.id.editTextTextDestinationAddress)
        spinnerRequestStatus = findViewById(R.id.spinnerRequestStatus)

        val statusEnum = arrayOf("SEND", "RECEIVED", "RUN", "CLOSED")

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, statusEnum
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRequestStatus.setAdapter(adapter)
        //spinner.setOnItemSelectedListener(this)
    }

    fun saveButton(view: View) {
        val rootNode = FirebaseDatabase.getInstance()
        val reference = rootNode.getReference("travels")

        val clientName = etClientName.text.toString()
        val clientPhone = etPhone.text.toString()
        val clientEmailAddress = etEmailAddress.text.toString()
        val departureAddress = etDepartureAddress.text.toString()
        val departureDate = etDepartureDate.text.toString()
        val destinationAddress = etDestinationAddress.text.toString()
        val returnDate = etReturnDate.text.toString()
        val passengersNumber = etPassengersNumber.text.toString()
        val requestStatus = spinnerRequestStatus.toString()

        val travel = Travel(
            clientName,
            clientPhone,
            clientEmailAddress,
            departureAddress,
            departureDate,
            destinationAddress,
            returnDate,
            passengersNumber,
            requestStatus
        )

        reference.push().setValue(travel)

        val text = "הפרטים נשמרו בהצלחה!"
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }

//    private val textWatcher = object : TextWatcher {
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            TODO("Not yet implemented")
//        }
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            buttonSave1.isEnabled = !(editTextClientName1.text.isEmpty() ||
//                    editTextPhone1.text.isEmpty() ||
//                    _editTextEmailAddress.text.isEmpty() ||
//                    _editTextDepartureDate.text.isEmpty() ||
//                    _editTextReturnDate.text.isEmpty() ||
//                    _editTextPassengersNumber.text.isEmpty() ||
//                    _editTextTextDepartureAddress.text.isEmpty() ||
//                    _editTextTextDestinationAddress.text.isEmpty() ||
//                    _editTextTextRequestStatus.text.isEmpty())
//        }
//
//        override fun afterTextChanged(s: Editable?) {
//            TODO("Not yet implemented")
//        }
//    }
}


