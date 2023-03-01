package com.example.roomreservation.model.remotedatasource

import com.example.roomreservation.Constant.URL_RESERVATION
import com.example.roomreservation.model.Reservation
import com.example.roomreservation.model.remotedatasource.services.ReservationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationRemoteDataSource {
    fun getReservations(id: String, onResult: (List<Reservation>?) -> Unit) {
        val retrofit = ServiceBuilder(URL_RESERVATION).buildService(ReservationService::class.java)
        retrofit.getReservationsByUserId(id).enqueue(
            object : Callback<List<Reservation>> {
                override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                    val reservations = response.body()
                    onResult(reservations)
                }
            }
        )
    }

    fun createReservation(reservation: Reservation, onResult: (Reservation?) -> Unit) {
        val retrofit = ServiceBuilder(URL_RESERVATION).buildService(ReservationService::class.java)
        retrofit.createReservation(reservation).enqueue(
            object : Callback<Reservation> {
                override fun onFailure(call: Call<Reservation>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Reservation>, response: Response<Reservation>) {
                    val reservation = response.body()
                    onResult(reservation)
                }
            }
        )
    }
}