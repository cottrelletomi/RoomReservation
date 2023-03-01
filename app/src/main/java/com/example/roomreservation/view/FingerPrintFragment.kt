package com.example.roomreservation.view

import android.Manifest
import android.app.KeyguardManager
import android.content.Context.FINGERPRINT_SERVICE
import android.content.Context.KEYGUARD_SERVICE
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.roomreservation.auth.FingerPrint
import com.example.roomreservation.R
import com.example.roomreservation.databinding.FragmentFingerPrintBinding
import com.example.roomreservation.auth.FingerPrintHandler

class FingerPrintFragment : Fragment() {
    private val TAG = "::FingerPrintFragment::"
    private val fingerPrint = FingerPrint()
    private lateinit var binding: FragmentFingerPrintBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFingerPrintBinding.inflate(layoutInflater)

        val keyguardManager = requireContext().getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        val fingerprintManager = requireContext().getSystemService(FINGERPRINT_SERVICE) as FingerprintManager

        if (!fingerprintManager.isHardwareDetected) {
            Toast.makeText(requireContext(), "Your device doesn't support fingerprint authentication", Toast.LENGTH_LONG).show()
        }

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(requireContext(), "Please enable the fingerprint permission", Toast.LENGTH_LONG).show()
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {
            Toast.makeText(requireContext(), "No fingerprint configured. Please register at least one fingerprint in your device's Settings", Toast.LENGTH_LONG).show()
        }

        if (!keyguardManager.isKeyguardSecure) {
            Toast.makeText(requireContext(), "Please enable lockscreen security in your device's Settings", Toast.LENGTH_LONG).show()
        } else {
            val keyStore = fingerPrint.getKeyStore()
            if (keyStore != null) {
                val cipher = fingerPrint.getCipher(keyStore)
                if (cipher != null) {
                    FingerPrintHandler(requireContext(), cipher) { success, message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        if (success) {
                            binding.root.findNavController().navigate(R.id.action_fingerPrintFragment_to_listRoomFragment)
                        } else {
                            Log.i(TAG, "ERROR_FINGERPRINT_AUTHENTIFICATION")
                        }
                    }
                }
            }
        }

        return binding.root
    }
}