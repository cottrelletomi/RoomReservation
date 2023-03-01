package com.example.roomreservation

object Constant {
    const val URL = "http://172.31.254.21:50"
    const val URL_USER = URL//"http://192.168.1.23:8484"
    const val URL_ROOM = URL//"http://192.168.1.23:8585"
    const val URL_RESERVATION = URL//"http://192.168.1.23:8181"

    const val AUTHORIZE_ENDPOINT = "http://172.31.254.21:40/realms/nameless-rooms/protocol/openid-connect/auth"
    const val TOKEN_ENDPOINT = "http://172.31.254.21:40/realms/nameless-rooms/protocol/openid-connect/token"
    const val LOGOUT_ENDPOINT = "http://172.31.254.21:40/realms/nameless-rooms/protocol/openid-connect/logout"
    const val CLIENT_ID = "mobile-front"
    const val REDIRECT_URI = "mydwpapp://menu.com"
    const val GRANT_TYPE = "authorization_code"
}