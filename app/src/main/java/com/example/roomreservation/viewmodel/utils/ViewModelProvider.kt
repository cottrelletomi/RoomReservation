package com.example.roomreservation.viewmodel.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.roomreservation.viewmodel.ListReservationViewModel
import com.example.roomreservation.viewmodel.ListRoomViewModel

object ViewModelProvider {
    fun listRoomViewModel(fragment: Fragment, context: Context): ListRoomViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelFactory {
                ListRoomViewModel()
            }
        )[ListRoomViewModel::class.java]
    }

    fun listReservationViewModel(fragment: Fragment, context: Context): ListReservationViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelFactory {
                ListReservationViewModel()
            }
        )[ListReservationViewModel::class.java]
    }
}