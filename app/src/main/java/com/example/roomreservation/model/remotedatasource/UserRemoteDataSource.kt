package com.example.roomreservation.model.remotedatasource

import com.example.roomreservation.Constant.URL_USER
import com.example.roomreservation.Session.ACCESS_TOKEN
import com.example.roomreservation.model.User
import com.example.roomreservation.model.remotedatasource.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSource {
    /*fun getUsers(onResult: (List<User>?) -> Unit) {
        val retrofit = ServiceBuilder(url).buildService(UserService::class.java)
        retrofit.getUsers().enqueue(
            object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    val users = response.body()
                    Log.d("users", users.toString())
                    onResult(users)
                }
            }
        )
    }

    fun getUsersByEmails(emails: List<String>, onResult: (List<User>?) -> Unit) {
        val retrofit = ServiceBuilder(url).buildService(UserService::class.java)
        retrofit.getUsersByEmails(emails).enqueue(
            object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    val users = response.body()
                    Log.d("users", users.toString())
                    onResult(users)
                }
            }
        )
    }*/

    fun getUser(onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder(URL_USER).buildService(UserService::class.java)
        val token = "Bearer ${ACCESS_TOKEN}"
        retrofit.getUser(token).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }

    fun setUser(user: User, onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder(URL_USER).buildService(UserService::class.java)
        val token = "Bearer ${ACCESS_TOKEN}"
        retrofit.setUser(token, user).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }

    fun syncUser(onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder(URL_USER).buildService(UserService::class.java)
        val token = "Bearer ${ACCESS_TOKEN}"
        retrofit.syncUser(token).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }

    fun setUserPicture(picture: String, onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder(URL_USER).buildService(UserService::class.java)
        val token = "Bearer ${ACCESS_TOKEN}"
        retrofit.setUserPicture(token, picture).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }

    fun getUsersByEmails(emails: List<String>, onResult: (List<User>?) -> Unit) {
        val retrofit = ServiceBuilder(URL_USER).buildService(UserService::class.java)
        val token = "Bearer ${ACCESS_TOKEN}"
        retrofit.getUsersByEmails(token, emails).enqueue(
            object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    val users = response.body()
                    onResult(users)
                }
            }
        )
    }

    fun setNotificationTokenUser(tokenNotification: String, onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder(URL_USER).buildService(UserService::class.java)
        val token = "Bearer ${ACCESS_TOKEN}"
        retrofit.setNotificationTokenUser(token, tokenNotification).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }
}