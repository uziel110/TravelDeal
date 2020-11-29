package ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.traveldeal.R
import data.entities.Travel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

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
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            try {
                picker.datePicker.minDate = sdf.parse(etDepartureDate.text.toString()).time
                picker.show()
            } catch (ex: ParseException) {
                Toast.makeText(
                    applicationContext,
                    R.string.enter_exit_date_first,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        etPassengersNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.toString().startsWith("0")) {
                    s.clear()
                    Toast.makeText(
                        applicationContext,
                        R.string.num_bigger_than_0,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })

        etEmailAddress.setOnFocusChangeListener { _, gainFocus ->
            //onFocus
            if (!gainFocus && !isValidEmail(etEmailAddress.text.toString()))
                Toast.makeText(
                    applicationContext,
                    R.string.incorrect_email,
                    Toast.LENGTH_SHORT
                )
                    .show()
            etEmailAddress.text.clear()
        }

        etPhone.setOnFocusChangeListener { _, gainFocus ->
            //onFocus
            if (!gainFocus && etPhone.text.length < 9 || etPhone.text.length > 10) {
                Toast.makeText(
                    applicationContext,
                    R.string.incorrect_phone_number,
                    Toast.LENGTH_SHORT
                ).show()
                etPhone.text.clear()
            }
        }

        ArrayAdapter.createFromResource(
            this, R.array.status_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerRequestStatus.adapter = adapter
        }
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

        if (clientName == "" ||
            clientPhone == "" ||
            !isValidEmail(clientEmailAddress) ||
            departureAddress == "" ||
            departureDate == "" ||
            destinationAddress == "" ||
            returnDate == "" ||
            passengersNumber == "" ||
            requestStatus == ""
        ) {
            Toast.makeText(
                applicationContext,
                R.string.fill_all_fields,
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }
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

        // TODO: 29-Nov-20 check success of upload
        Toast.makeText(applicationContext, R.string.saved_success, Toast.LENGTH_SHORT).show()
    }
}