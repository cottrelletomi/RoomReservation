package com.example.roomreservation.model.repositories

import com.example.roomreservation.model.Room
import com.example.roomreservation.model.remotedatasource.RoomRemoteDataSource

class RoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource
) {
    fun getRooms(onResult: (List<Room>?) -> Unit) {
        roomRemoteDataSource.getRooms(onResult)
    }

    fun getRoom(id: Int, onResult: (Room?) -> Unit) {
        roomRemoteDataSource.getRoom(id, onResult)
    }
}