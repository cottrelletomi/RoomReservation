package com.example.roomreservation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class Room(
    @PrimaryKey
    val id_room: Int,
    val name: String,
    val capacity: Int
)