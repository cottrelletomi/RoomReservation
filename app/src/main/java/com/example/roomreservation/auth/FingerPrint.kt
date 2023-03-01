package com.example.roomreservation.auth

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class FingerPrint {
    private val KEY_NAME = "1234"

    fun getKeyStore(): KeyStore? {
        var keyStore: KeyStore?
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyStore.load(null)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build())
            keyGenerator.generateKey()
        } catch (e: Exception) { keyStore = null }
        return keyStore
    }

    fun getCipher(keyStore: KeyStore): Cipher? {
        var cipher: Cipher?
        try {
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7)

            keyStore.load(null)
            val key = keyStore.getKey(KEY_NAME, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
        } catch (e: Exception) { cipher = null }
        /*catch (e: NoSuchAlgorithmException) {e.printStackTrace() }
        catch (e: NoSuchPaddingException) { e.printStackTrace() }
        catch (e: KeyPermanentlyInvalidatedException) { e.printStackTrace() }*/
        return cipher
    }
}