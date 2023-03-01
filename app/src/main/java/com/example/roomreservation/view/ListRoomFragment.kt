package com.example.roomreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomreservation.R
import com.example.roomreservation.databinding.FragmentListRoomBinding
import com.example.roomreservation.viewmodel.ListRoomViewModel
import com.example.roomreservation.viewmodel.utils.ViewModelFactory
import com.example.roomreservation.viewmodel.utils.ViewModelProvider
import com.example.roomreservation.view.adapters.RoomAdapter

class ListRoomFragment : Fragment() {
    private val TAG = "::ListRoomFragment::"
    private lateinit var binding: FragmentListRoomBinding
    private val model: ListRoomViewModel by viewModels {
        ViewModelFactory {
            ViewModelProvider.listRoomViewModel(this, requireContext())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        /*FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, token)
            userRepository.setNotificationTokenUser(USER_ID, token) { user ->
                if(user != null) {
                    Log.i(TAG, "Notification token of user is updated")
                } else {
                    Log.i(TAG, "Notification token of user is not updated")
                }
            }
        })*/

        binding = FragmentListRoomBinding.inflate(layoutInflater)
        binding.rooms.layoutManager = LinearLayoutManager(context) // this creates a vertical layout Manager
        model.rooms.observe(viewLifecycleOwner) { rooms ->
            binding.rooms.adapter = RoomAdapter(rooms) { room ->
                binding.root.findNavController().navigate(
                    R.id.action_listRoomFragment_to_roomReservationFragment,
                    RoomReservationFragment.generateBundle(room.id_room, room.name)
                )
            }
        }

        return binding.root
    }
}