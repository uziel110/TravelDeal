package utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import java.io.IOException
import java.util.*

/**
 * UserLocation class
 */
class UserLocation() {
    var lat: Double = 0.0
        private set
    var lon: Double = 0.0
        private set

    constructor(_lat: Double, _lon: Double) : this() {
        lat = _lat
        lon = _lon
    }
}

