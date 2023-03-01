package com.example.roomreservation.view

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.roomreservation.R
import com.example.roomreservation.databinding.FragmentEnrollBinding
import com.example.roomreservation.model.remotedatasource.UserRemoteDataSource
import com.example.roomreservation.model.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream

class EnrollFragment : Fragment() {
    private val TAG = "::EnrollFragment::"
    private lateinit var binding: FragmentEnrollBinding
    private val userRepository = UserRepository(UserRemoteDataSource())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentEnrollBinding.inflate(layoutInflater)

        Toast.makeText(requireContext(), "Vous devez d'abord vous enroller en enregistrant votre visage.", Toast.LENGTH_LONG).show()

        val requestCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_LONG).show()
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE)
                //requireActivity().setResult(AppCompatActivity.RESULT_OK)
            } else {
                Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_LONG).show()
                //requireActivity().finish()
            }
        }
        requestCamera.launch(android.Manifest.permission.CAMERA)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val extras = data?.extras
            val bitmap = extras?.get("data") as Bitmap
            val picture = bitmapToString(bitmap)
            userRepository.setUserPicture(picture) { user ->
                if(user != null) {
                    binding.root.findNavController().navigate(R.id.action_enrollFragment_to_listRoomFragment)
                }
            }
        }
    }

    private fun bitmapToString(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        runBlocking {
            launch(Dispatchers.IO) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            }.join()
        }
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }
}