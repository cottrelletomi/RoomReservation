package com.example.roomreservation

import com.example.roomreservation.model.User

object Session {
    //*** NOT RECOMMENDED : THESE WILL BE STORED TEMPORARILY FOR TEST PURPOSES !!!! ***///
    var REFRESH_TOKEN = ""
    var ACCESS_TOKEN = ""
    var user: User? = null
}