package com.example.roomreservation.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.roomreservation.Session
import com.example.roomreservation.databinding.FragmentRoomReservationBinding
import com.example.roomreservation.model.Reservation
import com.example.roomreservation.model.remotedatasource.ReservationRemoteDataSource
import com.example.roomreservation.model.remotedatasource.RoomRemoteDataSource
import com.example.roomreservation.model.remotedatasource.UserRemoteDataSource
import com.example.roomreservation.model.repositories.ReservationRepository
import com.example.roomreservation.model.repositories.RoomRepository
import com.example.roomreservation.model.repositories.UserRepository
import java.sql.Date
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class RoomReservationFragment : Fragment() {
    private val TAG = "::RoomReservationFragment::"
    private lateinit var userRepository: UserRepository
    private lateinit var roomRepository: RoomRepository
    private lateinit var reservationRepository: ReservationRepository
    private lateinit var binding: FragmentRoomReservationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        userRepository = UserRepository(UserRemoteDataSource())
        roomRepository = RoomRepository(RoomRemoteDataSource())
        reservationRepository = ReservationRepository(ReservationRemoteDataSource())

        binding = FragmentRoomReservationBinding.inflate(layoutInflater)
        val roomId = arguments?.getInt(ARG_ROOM_ID)
        val roomName = arguments?.getString(ARG_ROOM_NAME)
        Log.i("room",roomName.toString())
        if (roomName != null) {
            binding.roomName.text = roomName
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        var date: Date? = null
        var time: Time? = null
        var duration: Time? = null

        binding.txtViewDuration.setOnClickListener {
            val timePickerDialog = TimePickerDialog(requireContext(), { _, hour, minute ->
                duration = Time(hour, minute, 0)
                binding.txtViewDuration.text = "$hour:$minute"
            }, hour, minute, true)
            timePickerDialog.show()
        }

        binding.txtViewDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
                date = Date(year, month, day)
                binding.txtViewDate.text = "$day-$month-$year"
            }, year, month, day)
            datePickerDialog.show()
        }

        binding.txtViewTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(requireContext(), { _, hour, minute ->
                time = Time(hour, minute, 0)
                binding.txtViewTime.text = "$hour:$minute"
            }, hour, minute, true)
            timePickerDialog.show()
        }

        binding.btnReserve.setOnClickListener {
                if (roomId != null && duration != null && date != null && time != null) {
                    userRepository.getUser { user ->
                        if (user != null) {
                            roomRepository.getRoom(roomId) { room ->
                                if (room != null) {
                                    val emails = binding.editTxtGuests.text.split(",")
                                    userRepository.getUsersByEmails(emails) { users ->
                                        if (users != null) {
                                            calendar.set(date!!.year, date!!.month, date!!.day, time!!.hours, time!!.minutes)
                                            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.FRENCH)
                                            val outputDate = outputFormat.format(calendar.time)
                                            val outputDuration = duration!!.hours * 60 + duration!!.minutes
                                            val reservation = Reservation(user, room, outputDate, outputDuration, users)
                                            Log.d("reservation", reservation.toString())
                                            reservationRepository.createReservation(reservation) { res ->
                                                if(res != null) {
                                                    Toast.makeText(requireContext(),"Your reservation is saved", Toast.LENGTH_SHORT).show()
                                                } else { Log.i(TAG, "reservationRepository.createReservation(reservation)") }
                                            }
                                        } else { Log.i(TAG, "userRepository.getUsersByEmails(emails)") }
                                    }
                                } else { Log.i(TAG, "roomRepository.getRoom(roomId)") }
                            }
                        } else { Log.i(TAG, "userRepository.getUser") }
                    }
                }
        }
        return binding.root
    }

    companion object {
        private const val ARG_ROOM_ID = "room_id"
        private const val ARG_ROOM_NAME = "room_name"
        fun generateBundle(roomId: Int, roomName: String): Bundle {
            return bundleOf(ARG_ROOM_ID to roomId, ARG_ROOM_NAME to roomName)
        }
    }

}