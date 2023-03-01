package com.example.roomreservation.model.repositories

import com.example.roomreservation.model.Reservation
import com.example.roomreservation.model.remotedatasource.ReservationRemoteDataSource

class ReservationRepository(
    private val reservationRemoteDataSource: ReservationRemoteDataSource
) {
    fun getReservations(id: String, onResult: (List<Reservation>?) -> Unit) {
        reservationRemoteDataSource.getReservations(id, onResult)
    }

    fun createReservation(reservation: Reservation, onResult: (Reservation?) -> Unit) {
        reservationRemoteDataSource.createReservation(reservation, onResult)
    }
}