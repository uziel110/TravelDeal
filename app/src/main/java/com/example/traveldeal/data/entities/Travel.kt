package com.example.traveldeal.data.entities

import androidx.room.*
import androidx.room.Entity
import com.example.traveldeal.data.enums.Status
import utils.RequestStatusConverter
import utils.UserLocation
import utils.UserLocationConverter
import com.google.firebase.database.Exclude
import utils.CompanyConverter

//@TypeConverters(Travel.UserLocationConverter::class)
//private val travelLocation: UserLocation? = null

@Entity(tableName = "travels_table")
class Travel {
    @PrimaryKey
    var travelId: String = ""
        set
        get() = field
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
    @TypeConverters(UserLocationConverter::class)
    var departLocation: UserLocation? = null
        set
        get() = if (field != null) field else UserLocation(0.0, 0.0)
    var departureDate: String = ""
        set
        get() = field
    var destinationAddress: String = ""
        set
        get() = field
    @TypeConverters(UserLocationConverter::class)
    var destLocation: UserLocation? = null
        set
        get() = if (field != null) field else UserLocation(0.0, 0.0)
    var returnDate: String = ""
        set
        get() = field
    var passengersNumber: Int = 0
        set
        get() = field
    @TypeConverters(RequestStatusConverter::class)
    var requestStatus: Status = Status.SENT
        set
        get() = field
    @TypeConverters(CompanyConverter::class)
    var company = mutableMapOf<String, Boolean>()
        set
        get() = field
    // for expandable of card in recycle view
    @Ignore
    var expandable: Boolean = false
        @Exclude
        set
        @Exclude
        get() = field
}