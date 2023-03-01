package com.example.roomreservation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomreservation.model.Room
import com.example.roomreservation.model.remotedatasource.RoomRemoteDataSource
import com.example.roomreservation.model.repositories.RoomRepository

class ListRoomViewModel : ViewModel() {
    private val TAG = "::ListRoomViewModel::"
    private val roomRepository = RoomRepository(RoomRemoteDataSource())
    private val _rooms = MutableLiveData<List<Room>>()
    var rooms: LiveData<List<Room>> = _rooms

    init {
        this.loadRooms()
    }

    private fun loadRooms() {
        roomRepository.getRooms { rooms ->
            if(rooms != null) {
                Log.i(TAG, "list of rooms updated by the remote")
                this._rooms.value = rooms
            } else {
                Log.i("rooms", rooms.toString())
                Log.i(TAG, "list of rooms not updated by the remote")
            }
        }
    }
}