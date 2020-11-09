package entities

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

//@Travel.Entity(tableName = travel_table)
//    class Travel(@PrimaryKey @ColumnInfo(name = "Travel") val word: String){
//
//}

class Travel(){
    var clientName: String = ""
    var clientPhone: String = ""
    var clientEmailAddress: String = ""
    var departureDate : String = ""
    var returnDate : String = ""
    var passengersNumber : Int = 0
    var departureAddress : String = ""
    var destinationAddress : String = ""
    var requestStatus : String = ""
}