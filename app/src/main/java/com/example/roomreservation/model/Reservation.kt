package com.example.roomreservation.model

import androidx.room.Entity

@Entity(tableName = "meeting_schedule")
data class Reservation(
    val organisator: User,
    val room: Room,
    val date: String,
    val duration: Int,
    val guests: List<User>
)