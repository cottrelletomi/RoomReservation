package com.example.roomreservation.auth

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import android.util.Log
import androidx.core.app.ActivityCompat
import javax.crypto.Cipher

class FingerPrintHandler (context: Context, cipher: Cipher, private val callback: (Boolean, String) -> Unit): FingerprintManager.AuthenticationCallback() {
    private val TAG = "::FingerPrintHandler::"

    init {
        val fingerPrintManager = context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        val cryptoObject = FingerprintManager.CryptoObject(cipher)
        val cancellationSignal = CancellationSignal()
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "FINGERPRINT_PERMISSION_REFUSED")
        }
        fingerPrintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    @Deprecated("Deprecated in Java")
    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        callback(false, "Authentication error\n$errString")
    }

    @Deprecated("Deprecated in Java")
    override fun onAuthenticationFailed() {
        callback(false, "Authentication failed")
    }

    @Deprecated("Deprecated in Java")
    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        callback(false, "Authentication help\n$helpString")
    }

    @Deprecated("Deprecated in Java")
    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
        callback(true, "Success!")
    }
}