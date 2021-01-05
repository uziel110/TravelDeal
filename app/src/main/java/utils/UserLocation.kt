package utils

import android.location.Location

class UserLocation(lat: Double, lon: Double) {
    private var lat: Double? = lat
    private var lon: Double? = lon

    fun getLat(): Double {
        return lat!!
    }

    fun getLon(): Double {
        return lon!!
    }

    fun convertFromLocation(location: Location?): UserLocation? {
        return if (location == null) null else UserLocation(location.latitude, location.longitude)
    }
}
