package com.example.roomreservation.model.repositories

import com.example.roomreservation.model.User
import com.example.roomreservation.model.remotedatasource.UserRemoteDataSource

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource
) {

    /*fun getUsers(onResult: (List<User>?) -> Unit) {
        userRemoteDataSource.getUsers(onResult)
    }

    fun getUsersByEmails(emails: List<String>, onResult: (List<User>?) -> Unit) {
        userRemoteDataSource.getUsersByEmails(emails, onResult)
    }*/

    fun getUser(onResult: (User?) -> Unit) {
        userRemoteDataSource.getUser(onResult)
    }

    fun setUser(user: User, onResult: (User?) -> Unit) {
        userRemoteDataSource.setUser(user, onResult)
    }

    fun syncUser(onResult: (User?) -> Unit) {
        userRemoteDataSource.syncUser(onResult)
    }

    fun setUserPicture(picture: String, onResult: (User?) -> Unit) {
        userRemoteDataSource.setUserPicture(picture, onResult)
    }

    fun getUsersByEmails(emails: List<String>, onResult: (List<User>?) -> Unit) {
        userRemoteDataSource.getUsersByEmails(emails, onResult)
    }

    fun setNotificationTokenUser(notificationtoken: String, onResult: (User?) -> Unit) {
        userRemoteDataSource.setNotificationTokenUser(notificationtoken, onResult)
    }
}