package com.example.roomreservation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.roomreservation.auth.Auth
import com.example.roomreservation.model.remotedatasource.UserRemoteDataSource
import com.example.roomreservation.model.repositories.UserRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "channel_id"
    private val TAG = "::MainActivity::"
    private lateinit var userRepository: UserRepository

    private val auth = Auth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRepository = UserRepository(UserRemoteDataSource())
        createNotificationChannel()

        menuHandler()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigationHandler(bottomNavigationView)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if(intent != null && intent.data != null) {
            intent.data!!.getQueryParameter("code")?.let { code ->
                auth.getToken(code) { accessToken, refreshToken ->
                    if (accessToken != null && refreshToken != null) {
                        Session.ACCESS_TOKEN = accessToken
                        Session.REFRESH_TOKEN = refreshToken

                        userRepository.syncUser { user ->
                            Session.user = user
                            updateNotificationTokenUser()
                            findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_listRoomFragment)
                            /*if(user != null && user.enrolled) {
                                findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_fingerPrintFragment)
                            } else {
                                findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_enrollFragment)
                            }*/
                        }
                    }
                }
            }
        }
    }

    private fun menuHandler() {
        val menuHost: MenuHost = this
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_logout, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.logout -> {
                        auth.logout {
                            Session.REFRESH_TOKEN = ""
                            Session.ACCESS_TOKEN = ""
                            Session.user = null
                            Log.i(TAG, "Disconnected")
                            //findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_loginFragment)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, this, Lifecycle.State.RESUMED)
    }

    private fun navigationHandler(bottomNavigationView: BottomNavigationView) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.listRoomFragment ||
                destination.id == R.id.listReservationFragment) {
                bottomNavigationView.visibility = View.VISIBLE
            } else { bottomNavigationView.visibility = View.GONE }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_room -> {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_listRoomFragment)
                    true
                }
                R.id.action_reservation -> {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_listReservationFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun updateNotificationTokenUser() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG + "(notificationToken)>>>", token)
            userRepository.setNotificationTokenUser(token) { user ->
                if(user != null) {
                    Log.i(TAG, "Notification token of user is updated")
                } else {
                    Log.i(TAG, "Notification token of user is not updated")
                }
            }
        })
    }
}