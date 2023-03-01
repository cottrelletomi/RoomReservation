package com.example.roomreservation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomreservation.databinding.FragmentListReservationBinding
import com.example.roomreservation.view.adapters.ReservationAdapter
import com.example.roomreservation.viewmodel.ListReservationViewModel
import com.example.roomreservation.viewmodel.utils.ViewModelFactory
import com.example.roomreservation.viewmodel.utils.ViewModelProvider

class ListReservationFragment : Fragment() {
    private val TAG = "::ListReservationFragment::"
    private lateinit var binding: FragmentListReservationBinding
    private val model: ListReservationViewModel by viewModels {
        ViewModelFactory {
            ViewModelProvider.listReservationViewModel(this, requireContext())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentListReservationBinding.inflate(layoutInflater)
        binding.reservations.layoutManager = LinearLayoutManager(context) // this creates a vertical layout Manager
        model.reservations.observe(viewLifecycleOwner) { reservations ->
            binding.reservations.adapter = ReservationAdapter(reservations) { reservation ->
                Log.i(TAG, reservation.toString())
            }
        }

        return binding.root
    }
}