package com.example.traveldeal.data.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.traveldeal.R
import com.example.traveldeal.data.entities.Travel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import utils.UserLocation
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddTravelActivity : AppCompatActivity() {

    private lateinit var viewModel: TravelViewModel
    private lateinit var saveButton: Button
    private lateinit var etClientName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmailAddress: EditText
    private lateinit var etDepartureDate: EditText
    private lateinit var etReturnDate: EditText
    private lateinit var etPassengersNumber: EditText
    private lateinit var etDepartureAddress: EditText
    private lateinit var etDestinationAddress: EditText

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_travel)

        viewModel = ViewModelProvider(this).get(TravelViewModel::class.java)
        viewModel.getLiveData().observe(this, {
            if (it) {
                Log.d("FirebaseManager", "Upload Successful")
                Toast.makeText(applicationContext, R.string.saved_success, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("FirebaseManager", "Upload Fail")
                Toast.makeText(applicationContext, "Saved fail", Toast.LENGTH_SHORT).show()
            }
        })

        saveButton = findViewById(R.id.buttonSave)
        etClientName = findViewById(R.id.editTextClientName)
        etPhone = findViewById(R.id.editTextPhone)
        etEmailAddress = findViewById(R.id.editTextEmailAddress)
        etReturnDate = findViewById(R.id.editTextReturnDate)
        etDepartureDate = findViewById(R.id.editTextDepartureDate)
        etPassengersNumber = findViewById(R.id.editTextPassengersNumber)
        etDepartureAddress = findViewById(R.id.editTextTextDepartureAddress)
        etDestinationAddress = findViewById(R.id.editTextTextDestinationAddress)

        val user = Firebase.auth.currentUser
        etEmailAddress.setText(user?.email)
        etClientName.setText(user?.displayName)
        etPhone.setText(user?.phoneNumber)
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
            if (!gainFocus && !isValidEmail(etEmailAddress.text.toString())) {
                Toast.makeText(
                    applicationContext,
                    R.string.incorrect_email,
                    Toast.LENGTH_SHORT
                )
                    .show()
                etEmailAddress.text.clear()
            }
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
    }

    fun saveButton(view: View) {

        val clientName = etClientName.text.toString()
        val clientPhone = etPhone.text.toString()
        val clientEmailAddress = etEmailAddress.text.toString()
        val departureAddress = etDepartureAddress.text.toString()
        val destinationAddress = etDestinationAddress.text.toString()
        val departureDate = etDepartureDate.text.toString()
        val returnDate = etReturnDate.text.toString()
        val passengersNumber = etPassengersNumber.text.toString()

        lateinit var travelLocation: Location
        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        try {
            val l: List<Address> = geocoder.getFromLocationName(departureAddress, 1);
            if (!l.isEmpty()) {
                val temp: Address = l.get(0)
                travelLocation = Location("travelLoction")
                travelLocation.setLatitude(temp.getLatitude())
                travelLocation.setLongitude(temp.getLongitude())

            } else {
                Toast.makeText(this, "4:Unable to understand address", Toast.LENGTH_LONG).show()
                return
            }
        } catch (e: IOException) {
            Toast.makeText(
                this,
                "5:Unable to understand address. Check Internet connection.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        lateinit var userLoc: UserLocation
        userLoc.convertFromLocation(travelLocation)

        if (clientName == "" ||
            clientPhone == "" ||
            !isValidEmail(clientEmailAddress) ||
            departureAddress == "" ||
            departureDate == "" ||
            destinationAddress == "" ||
            returnDate == "" ||
            passengersNumber == ""
        ) {
            Toast.makeText(
                applicationContext,
                R.string.fill_all_fields,
                Toast.LENGTH_SHORT
            ).show()
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
            "Send"
        )

        viewModel.insertItem(travel)
    }
}