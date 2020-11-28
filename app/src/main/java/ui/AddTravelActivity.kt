package ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.traveldeal.R
import data.entities.Travel
import java.util.*


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
        //etDepartureDate = findViewById(R.id.editTextDepartureDate)
        etReturnDate = findViewById(R.id.editTextReturnDate)
        etPassengersNumber = findViewById(R.id.editTextPassengersNumber)
        etDepartureAddress = findViewById(R.id.editTextTextDepartureAddress)
        etDestinationAddress = findViewById(R.id.editTextTextDestinationAddress)
        spinnerRequestStatus = findViewById(R.id.spinnerRequestStatus)

        var picker: DatePickerDialog
        etDepartureDate = findViewById<View>(R.id.editTextDepartureDate
        ) as EditText
        etDepartureDate.setInputType(InputType.TYPE_NULL)
        etDepartureDate.setOnClickListener(View.OnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(
                this,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth -> etDepartureDate.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker.show()
        })


        ArrayAdapter.createFromResource(

            this, R.array.status_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerRequestStatus.adapter = adapter
        }




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



