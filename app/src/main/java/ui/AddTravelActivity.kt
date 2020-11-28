package ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.traveldeal.R
import data.entities.Travel

//import com.emedinaa.kotlinmvvm.di.Injection


class AddTravelActivity : AppCompatActivity() {

    lateinit var viewModel: TravelViewModel
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

        //viewModel = ViewModelProvider(this)[TravelViewModel::class.java]
        viewModel = ViewModelProvider(this).get(TravelViewModel::class.java)

//        val isSuccess: LiveData<Travel> = viewModel.getItems()
//        isSuccess.observe(this,
//            Observer<Travel> {
//                Toast.makeText(applicationContext, "הפרטים נשמרו בהצלחה!", Toast.LENGTH_SHORT)
//                    .show()
//
//            })


//            viewModel = ViewModelProvider(
//                this,
//                Injection.provideViewModelFactory()
//            ).get(TravelViewModel::class.java)

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


//        saveButton.addTextChangedListener(textWatcher)
        //etClientName.addTextChangedListener(textWatcher)
//        etPhone.addTextChangedListener(textWatcher)
//        etEmailAddress.addTextChangedListener(textWatcher)
//        etDepartureDate.addTextChangedListener(textWatcher)
//        etReturnDate.addTextChangedListener(textWatcher)
//        etPassengersNumber.addTextChangedListener(textWatcher)
//        etDepartureAddress.addTextChangedListener(textWatcher)
//        etDestinationAddress.addTextChangedListener(textWatcher)
//        spinnerRequestStatus.toString().addTextChangedListener(textWatcher)
    }


    fun saveButton(view: View) {

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


        viewModel.insertItem(travel)

        val text = "הפרטים נשמרו בהצלחה!"
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }


//    val textWatcher = object : TextWatcher {
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            val tex = etClientName.text.toString().trim()
//            saveButton.isEnabled = !(
//                    tex.isEmpty())
////                    ||etPhone.text.toString().trim().isEmpty() ||
////                    etEmailAddress.text.toString().trim().isEmpty() ||
////                    etDepartureDate.text.toString().trim().isEmpty() ||
////                    etReturnDate.text.toString().trim().isEmpty() ||
////                    etPassengersNumber.text.toString().trim().isEmpty() ||
////                    etDepartureAddress.text.toString().trim().isEmpty() ||
////                    etDestinationAddress.text.toString().trim().isEmpty() ||
////                    spinnerRequestStatus.toString().trim().isEmpty())
//        }
//
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            TODO("Not yet implemented")
//        }
//
//        override fun afterTextChanged(s: Editable?) {
//            TODO("Not yet implemented")
//        }
//    }
}



