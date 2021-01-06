package com.example.traveldeal.data.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import utils.UserLocation
//import utils.DestinationAddressActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class AddTravelActivity : AppCompatActivity() {
    val api = "AIzaSyBlm-gYIse1zkWi3WwqQg3w9UOxRm4P3pE"

    private lateinit var viewModel: TravelViewModel
    private lateinit var saveButton: Button
    private lateinit var etClientName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmailAddress: EditText
    private lateinit var etDepartureDate: EditText
    private lateinit var etReturnDate: EditText
    private lateinit var etPassengersNumber: EditText
//    private lateinit var etDepartureAddress: EditText
//    private lateinit var etDestinationAddress: EditText
//    private lateinit var acDepartureAddress
//    private lateinit var acDestinationAddress: EditText

    private lateinit var bSourceAddress: Button
    private lateinit var bDestination: Button

    var destinationAddress: String = ""
    var departureAddress: String = ""
    lateinit var destinationLocation: UserLocation
    lateinit var departureLocation: UserLocation

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private var isSourceAddress by Delegates.notNull<Boolean>()

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
//        etDepartureAddress = findViewById(R.id.editTextTextDepartureAddress)
//        etDestinationAddress = findViewById(R.id.editTextTextDestinationAddress)
//        acDestinationAddress = findViewById(R.id.autocomplete_DepartureAddress)
//        acDestinationAddress = findViewById(R.id.autocomplete_DestinationAddress)

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
//
//        etDepartureAddress.setOnClickListener {
//            isSourceAddress = true
//            startActivity(
//                Intent(this, AddressDialog::class.java).putExtra("bool", isSourceAddress)
//            )
//        }
//        etDestinationAddress.setOnClickListener {
//            isSourceAddress = false
//            startActivity(
//                Intent(this, AddressDialog::class.java).putExtra("bool", isSourceAddress)
//            )
//        }

        Places.initialize(applicationContext, api)

        // Initialize the AutocompleteSupportFragment.
        val departureAddressAutocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_DepartureAddress)
                    as AutocompleteSupportFragment
        departureAddressAutocompleteFragment.setHint("כתובת יציאה")//.setText("כתובת יציאה")

        // Specify the types of place data to return.
        departureAddressAutocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )
        // Set up a PlaceSelectionListener to handle the response.
        departureAddressAutocompleteFragment.setOnPlaceSelectedListener(object :
            PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                departureAddress = place.address.toString()
                departureLocation = place.latLng?.let { UserLocation(it.latitude, it.longitude) }!!
            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Toast.makeText(applicationContext, "Connection error", Toast.LENGTH_SHORT).show()
            }
        })

        // Initialize the AutocompleteSupportFragment.
        val destinationAddressAutocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_DestinationAddress)
                    as AutocompleteSupportFragment
        destinationAddressAutocompleteFragment.setHint("כתובת חזרה")//.setText("כתובת חזרה")

        // Specify the types of place data to return.
        destinationAddressAutocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )
        // Set up a PlaceSelectionListener to handle the response.
        destinationAddressAutocompleteFragment.setOnPlaceSelectedListener(object :
            PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                destinationAddress = place.address.toString()
                destinationLocation = place.latLng?.let { UserLocation(it.latitude, it.longitude) }!!
            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Toast.makeText(applicationContext, "Connection error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun saveButton(view: View) {

        val clientName = etClientName.text.toString()
        val clientPhone = etPhone.text.toString()
        val clientEmailAddress = etEmailAddress.text.toString()
//        val departureAddress = etDepartureAddress.text.toString()
//        val destinationAddress = etDestinationAddress.text.toString()
        val departureDate = etDepartureDate.text.toString()
        val returnDate = etReturnDate.text.toString()
        val passengersNumber = etPassengersNumber.text.toString()

        /*
        lateinit var departureLocation : UserLocation
        departureLocation.locationFromAddress(applicationContext,departureAddress)
        lateinit var destinationLocation : UserLocation
        destinationLocation.locationFromAddress(applicationContext,destinationAddress)
*/
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
            departureLocation,
            departureDate,
            destinationAddress,
            destinationLocation,
            returnDate,
            passengersNumber,
            resources.getStringArray(R.array.status_array)[0]
        )

        viewModel.insertItem(travel)
    }
}