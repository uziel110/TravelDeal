package utils

import androidx.room.TypeConverter

/**
 * UserLocation converter class
 * convert from UserLocation to string and vice versa
 */
class UserLocationConverter {
    @TypeConverter
    fun fromString(value: String?): UserLocation? {
        if (value == null || value == "") return null
        val lat = value.split(" ").toTypedArray()[0].toDouble()
        val lang = value.split(" ").toTypedArray()[1].toDouble()
        return UserLocation(lat, lang)
    }

    @TypeConverter
    fun asString(userLocation: UserLocation?): String {
        return if (userLocation == null) "" else userLocation.lat
            .toString() + " " + userLocation.lon
    }
}

/**
 * RequestStatus converter class
 * convert from Status to string and vice versa
 */
class RequestStatusConverter {
    @TypeConverter
    fun getStatus(status: Int): Status? {
        return when (status) {
            0 -> Status.SENT
            1 -> Status.RECEIVED
            2 -> Status.RUNNING
            3 -> Status.CLOSED
            4 -> Status.PAID
            else -> null
        }
    }

    @TypeConverter
    fun getTypeInt(status: Status): Int {
        return status.ordinal
    }
}

/**
 * CompanyConverter converter class
 * convert from MutableMap to string and vice versa
 */
class CompanyConverter {
    @TypeConverter
    fun fromString(value: String?): MutableMap<String, Boolean>? {
        if (value == null || value.isEmpty()) return null
        val mapString =
            value.split(",").toTypedArray() //split map into array of (string,boolean) strings
        val hashMap: MutableMap<String, Boolean> = HashMap()
        for (s1 in mapString)  //for all (string,boolean) in the map string
        {
            if (s1.isNotEmpty()) { //is empty maybe will needed because the last char in the string is ","
                val s2 = s1.split(":")
                    .toTypedArray() //split (string,boolean) to company string and boolean string.
                val aBoolean = java.lang.Boolean.parseBoolean(s2[1])
                hashMap[s2[0]] = aBoolean
            }
        }
        return hashMap
    }

    @TypeConverter
    fun asString(map: MutableMap<String?, Boolean?>?): String? {
        if (map == null) return null
        val mapString = StringBuilder()
        for ((key, value) in map.entries) mapString.append(
            key
        ).append(":").append(value).append(",")
        return mapString.toString()
    }
}