package com.example.roomreservation.model.remotedatasource.services

import com.example.roomreservation.model.Reservation
import retrofit2.Call
import retrofit2.http.*

interface ReservationService {
    @GET("/api/v1/reservations")
    fun getReservationsByUserId(@Query("uid") uid: String): Call<List<Reservation>>

    @GET("/api/v1/reservations/{id}")
    fun getReservation(@Path("id") id: Int): Call<Reservation>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/reservations")
    fun createReservation(@Body reservation: Reservation): Call<Reservation>
}