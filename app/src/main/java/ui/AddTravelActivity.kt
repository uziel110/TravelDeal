package ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.traveldeal.R
import data.entities.Travel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Locale

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


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_travel)

        viewModel = ViewModelProvider(this).get(TravelViewModel::class.java)

        saveButton = findViewById(R.id.buttonSave)
        etClientName = findViewById(R.id.editTextClientName)
        etPhone = findViewById(R.id.editTextPhone)
        etEmailAddress = findViewById(R.id.editTextEmailAddress)
        etReturnDate = findViewById(R.id.editTextReturnDate)
        etDepartureDate = findViewById(R.id.editTextDepartureDate)
        etPassengersNumber = findViewById(R.id.editTextPassengersNumber)
        etDepartureAddress = findViewById(R.id.editTextTextDepartureAddress)
        etDestinationAddress = findViewById(R.id.editTextTextDestinationAddress)
        spinnerRequestStatus = findViewById(R.id.spinnerRequestStatus)

        var picker: DatePickerDialog

        //etDepartureDate.inputType = InputType.TYPE_NULL

        etDepartureDate.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(
                this,
                { _, theYear, monthOfYear, dayOfMonth ->
                    etDepartureDate.setText("$dayOfMonth/${monthOfYear + 1}/$theYear")
                },
                year, month, day
            )
            picker.datePicker.minDate = System.currentTimeMillis() - 1000
            picker.show()
        }

        etReturnDate.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(
                this,
                { _, theYear, monthOfYear, dayOfMonth ->
                    etReturnDate.setText("$dayOfMonth/${monthOfYear + 1}/$theYear")
                },
                year, month, day
            )
            picker.datePicker.minDate =
                SimpleDateFormat("dd/MM/yyyy").parse(etDepartureDate.text.toString()).time
            picker.show()
        }


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



