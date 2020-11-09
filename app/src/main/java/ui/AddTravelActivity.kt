package ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.traveldeal.R

class AddTravelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_travel)
    }

    fun saveButton(view: View) {
        val text = "כמעט הפרטים נשמרו בהצלחה!"
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }

//    val buttonSave1 = findViewById<Button>(R.id.buttonSave)
//    val editTextClientName1 = findViewById<EditText>(R.id.editTextClientName)
//    val editTextPhone1 = findViewById<EditText>(R.id.editTextPhone)
//    val _editTextEmailAddress = findViewById<EditText>(R.id.editTextEmailAddress)
//    val _editTextDepartureDate = findViewById<EditText>(R.id.editTextDepartureDate)
//    val _editTextReturnDate = findViewById<EditText>(R.id.editTextReturnDate)
//    val _editTextPassengersNumber = findViewById<EditText>(R.id.editTextPassengersNumber)
//    val _editTextTextDepartureAddress = findViewById<EditText>(R.id.editTextTextDepartureAddress)
//    val _editTextTextDestinationAddress = findViewById<EditText>(R.id.editTextTextDestinationAddress)
//    val _editTextTextRequestStatus = findViewById<EditText>(R.id.editTextTextRequestStatus)

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