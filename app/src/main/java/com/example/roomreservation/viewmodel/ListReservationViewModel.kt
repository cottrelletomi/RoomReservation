package com.example.roomreservation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomreservation.Session
import com.example.roomreservation.model.Reservation
import com.example.roomreservation.model.remotedatasource.ReservationRemoteDataSource
import com.example.roomreservation.model.repositories.ReservationRepository

class ListReservationViewModel : ViewModel() {
    private val USER_ID = Session.user?.id_user
    private val TAG = "::ListReservationViewModel::"
    private val repository = ReservationRepository(ReservationRemoteDataSource())
    private val _reservations = MutableLiveData<List<Reservation>>()
    var reservations: LiveData<List<Reservation>> = _reservations

    init {
        this.loadReservations()
    }

    private fun loadReservations() {
        repository.getReservations(USER_ID!!) { reservations ->
            if(reservations != null) {
                Log.i(TAG, "list of reservations updated by the remote")
                this._reservations.value = reservations
            } else {
                Log.i("reservations", reservations.toString())
                Log.i(TAG, "list of reservations not updated by the remote")
            }
        }
    }
}