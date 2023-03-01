package com.example.roomreservation.model.remotedatasource

import com.example.roomreservation.Constant.URL_ROOM
import com.example.roomreservation.model.Room
import com.example.roomreservation.model.remotedatasource.services.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomRemoteDataSource {
    fun getRooms(onResult: (List<Room>?) -> Unit) {
        val retrofit = ServiceBuilder(URL_ROOM).buildService(RoomService::class.java)
        retrofit.getRooms().enqueue(
            object : Callback<List<Room>> {
                override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
                    val rooms = response.body()
                    onResult(rooms)
                }
            }
        )
    }

    fun getRoom(id: Int, onResult: (Room?) -> Unit) {
        val retrofit = ServiceBuilder(URL_ROOM).buildService(RoomService::class.java)
        retrofit.getRoom(id).enqueue(
            object : Callback<Room> {
                override fun onFailure(call: Call<Room>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Room>, response: Response<Room>) {
                    val room = response.body()
                    onResult(room)
                }
            }
        )
    }
}