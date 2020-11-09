package entities

class Travel() {
    var clientName: String = ""
        get() = field
    var clientPhone: String = ""
        get() = field
    var clientEmailAddress: String = ""
        get() = field
    var departureAddress: String = ""
        get() = field
    var departureDate: String = ""
        get() = field
    var destinationAddress: String = ""
        get() = field
    var returnDate: String = ""
        get() = field
    var passengersNumber: Int = 0
        get() = field
    var requestStatus: String = ""
        get() = field

    constructor(
        _name: String,
        _phone: String,
        _eMail: String,
        _departureAddress: String,
        _departureDate: String,
        _destAddress: String,
        _returnDate: String,
        _passNum: String,
        _status: String
    ) : this() {
        clientName = _name
        clientPhone = _phone
        clientEmailAddress = _eMail
        departureAddress = _departureAddress
        departureDate = _departureDate
        destinationAddress = _destAddress
        returnDate = _returnDate
        passengersNumber = if(_passNum == "") 0 else _passNum.toInt()
        requestStatus = _status
    }
}