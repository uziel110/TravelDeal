package com.example.traveldeal.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.firebase.database.Exclude
import utils.UserLocation

//@TypeConverters(Travel.UserLocationConverter::class)
//private val travelLocation: UserLocation? = null

@Entity(tableName = "travels")
class Travel {
    @PrimaryKey
    var clientId: String = ""
        set
        get() = field
    var clientName: String = ""
        set
        get() = field
    var clientPhone: String = ""
        set
        get() = field
    var clientEmailAddress: String = ""
        set
        get() = field
    var departureAddress: String = ""
        set
        get() = field
    var departLocation: UserLocation? = null
        set
        get() = if (field!= null) field else UserLocation(0.0,0.0)
    var departureDate: String = ""
        set
        get() = field
    var destinationAddress: String = ""
        set
        get() = field
    var destLocation: UserLocation? = null
        set
        get() = if (field!= null) field else UserLocation(0.0,0.0)
    var returnDate: String = ""
        set
        get() = field
    var passengersNumber: Int = 0
        set
        get() = field
    var requestStatus: String = ""
        set
        get() = field
    // for expandable of card in recycle view
//    var expandable: Boolean = false
//        @Exclude
//        set
//        get() = field

//    var company: HashMap<String, Boolean> = hashMapOf()
//        set
//        get() = field

    constructor()
//    constructor(
//        _name: String,
//        _phone: String,
//        _eMail: String,
//        _departureAddress: String,
//        _departLocation: UserLocation,
//        _departureDate: String,
//        _destAddress: String,
//        _destLocation: UserLocation,
//        _returnDate: String,
//        _passNum: String,
//        _requestStatus: String
//    ) : this() {
//        clientName = _name
//        clientPhone = _phone
//        clientEmailAddress = _eMail
//        departureAddress = _departureAddress
//        departLocation = _departLocation
//        departureDate = _departureDate
//        destinationAddress = _destAddress
//        destLocation = _destLocation
//        returnDate = _returnDate
//        passengersNumber = if (_passNum == "") 0 else _passNum.toInt()
//        requestStatus = _requestStatus
//    }

    class UserLocationConverter {
        @TypeConverter
        fun fromString(value: String?): UserLocation? {
            if (value == null || value == "") return null
            val lat = value.split(" ").toTypedArray()[0].toDouble()
            val lang = value.split(" ").toTypedArray()[1].toDouble()
            return UserLocation(lat, lang)
        }

        @TypeConverter
        fun asString(warehouseUserLocation: UserLocation?): String {
            return if (warehouseUserLocation == null) "" else warehouseUserLocation.lat
                .toString() + " " + warehouseUserLocation.lon
        }
    }
}