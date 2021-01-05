package com.example.traveldeal.data.entities

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import utils.UserLocation


@TypeConverters(Travel.UserLocationConverter::class)
private val travelLocation: UserLocation? = null

class Travel() {
    var clientName: String = ""
        get() = field
        set(value) {
            field = value
        }
    var clientPhone: String = ""
        get() = field
        set(value) {
            field = value
        }
    var clientEmailAddress: String = ""
        get() = field
        set(value) {
            field = value
        }
    var departureAddress: String = ""
        get() = field
        set(value) {
            field = value
        }
    var departureDate: String = ""
        get() = field
        set(value) {
            field = value
        }
    var destinationAddress: String = ""
        get() = field
        set(value) {
            field = value
        }
    var returnDate: String = ""
        get() = field
        set(value) {
            field = value
        }
    var passengersNumber: Int = 0
        get() = field
        set(value) {
            field = value
        }
    var requestStatus: String = ""
        get() = field
        set(value) {
            field = value
        }
    /*
    var company: HashMap<String, Boolean> = TODO()
        get() = field
        set(value) {
            field = value
        }
*/
    constructor(
        _name: String,
        _phone: String,
        _eMail: String,
        _departureAddress: String,
        _departureDate: String,
        _destAddress: String,
        _returnDate: String,
        _passNum: String,
        _requestStatus: String
    ) : this() {
        clientName = _name
        clientPhone = _phone
        clientEmailAddress = _eMail
        departureAddress = _departureAddress
        departureDate = _departureDate
        destinationAddress = _destAddress
        returnDate = _returnDate
        passengersNumber = if (_passNum == "") 0 else _passNum.toInt()
        requestStatus = _requestStatus
    }

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
            return if (warehouseUserLocation == null) "" else warehouseUserLocation.getLat()
                .toString() + " " + warehouseUserLocation.getLon()
        }
    }
}