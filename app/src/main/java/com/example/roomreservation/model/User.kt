package com.example.roomreservation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id_user: String,
    val firstname: String,
    val lastname: String,
    val picture: String,
    val email: String,
    val enrolled: Boolean,
    val password: String,
    val notificationToken: String
)