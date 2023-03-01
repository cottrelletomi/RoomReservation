package com.example.roomreservation.model.remotedatasource.services

import com.example.roomreservation.model.Room
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomService {
    @GET("/api/v1/rooms")
    fun getRooms(): Call<List<Room>>

    @GET("/api/v1/rooms/{id}")
    fun getRoom(@Path("id") id: Int): Call<Room>
}