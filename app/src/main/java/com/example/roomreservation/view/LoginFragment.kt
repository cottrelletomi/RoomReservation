package com.example.roomreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.roomreservation.auth.Auth
import com.example.roomreservation.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val TAG = "::LoginFragment::"
    private lateinit var binding: FragmentLoginBinding
    private val auth = Auth()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.login.setOnClickListener {
            auth.accessPage(requireContext())
        }
        return binding.root
    }
}