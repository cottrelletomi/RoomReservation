package com.example.roomreservation.model.remotedatasource.services

import com.example.roomreservation.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    /*@GET("/api/v1/users/all")
    fun getUsers(): Call<List<User>>

    @GET("/api/v1/users")
    fun getUser(@Header("Authorization") token: String): Call<User>

    @PUT("/api/v1/users/picture")
    fun setUserPicture(@Header("Authorization") token: String, @Body picture: String): Any

    @GET("/api/v1/users/all")
    fun getAllUsers(): Call<List<User>>

    /*@GET("/api/v1/users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>*/

    @POST("/api/v1/users/emails")
    fun getUsersByEmails(@Body emails: List<String>): Call<List<User>>*/

    @GET("/api/v1/users")
    fun getUser(@Header("Authorization") token: String): Call<User>

    @PUT("/api/v1/users")
    fun setUser(@Header("Authorization") token: String, @Body user: User): Call<User>

    @GET("/api/v1/users/sync")
    fun syncUser(@Header("Authorization") token: String): Call<User>

    @PUT("/api/v1/users/picture")
    fun setUserPicture(@Header("Authorization") token: String, @Body picture: String): Call<User>

    @POST("/api/v1/users/emails")
    fun getUsersByEmails(@Header("Authorization") token: String, @Body emails: List<String>): Call<List<User>>

    @PUT("/api/v1/users/notificationToken")
    fun setNotificationTokenUser(@Header("Authorization") token: String, @Body tokenNotification: String): Call<User>
}