package com.example.roomreservation.auth

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import com.example.roomreservation.Constant
import com.example.roomreservation.Session
import com.example.roomreservation.Session.ACCESS_TOKEN
import com.example.roomreservation.Session.REFRESH_TOKEN
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URLEncoder

class Auth {
    private val TAG = "::Auth::"

    fun accessPage(context: Context) {
        fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")

        val parameters = mapOf(
            "client_id" to Constant.CLIENT_ID,
            "redirect_uri" to Constant.REDIRECT_URI,
            "response_type" to "code",
            "scope" to "email openid profile",
            "code_challenge" to PKCEUtil.getCodeChallenge(), // Set the code challenge
            "code_challenge_method" to "S256"
        ).map { (k, v) -> "${(k.utf8())}=${v.utf8()}" }.joinToString("&")

        // Initiate the OAuth 2.0 flow using CustomTabs<url>.
        // Check that CustomTabs are supported!
        CustomTabsIntent.Builder().build().launchUrl(
            context,
            Uri.parse("${Constant.AUTHORIZE_ENDPOINT}?$parameters")
        )
    }

    fun getToken(code: String, callback: (String?, String?) -> Unit) {
        // Perform the token request on a background thread.
        Thread {
            val client = OkHttpClient()
            val request = buildRequest(code)
            val response = client.newCall(request).execute()
            if (response.body != null) {
                val gson = Gson()
                // Marshal the response from the token endpoint.
                val tokenResponse = gson.fromJson(response.body?.string(), TokenResponse::class.java)

                Log.d(TAG, tokenResponse.toString())

                // Split the ID Token to get attempt to get the payload.
                val segments = tokenResponse.idToken?.split(".")?.toTypedArray()

                // Verify the ID Token has the correct number of segments.
                if (segments?.size == 3) {
                    // save the token for later api calls ( TO DO : use SharedPreferences to save the token)
                    callback(tokenResponse.accessToken, tokenResponse.refreshToken)
                } else {
                    callback(null, null)
                    // Invalid number of segments so ignore the token as it is invalid.
                    //intent.putExtra(getString(R.string.payload), "Invalid Payload")
                }
            }
        }.start()
    }

    private fun buildRequest(code: String): Request {
        // Get the form body passing in the code verifier.
        val body = FormBody.Builder()
            .add("client_id", Constant.CLIENT_ID)
            .add("redirect_uri", Constant.REDIRECT_URI)
            .add("grant_type", Constant.GRANT_TYPE)
            .add("code_verifier", PKCEUtil.getCodeVerifier()) // Send the code verifier
            .add("code", code)
            .build()

        // Build the request ensuring the content type is set to `application/x-www-form-urlencoded`
        // and the form body
        val request = Request.Builder()
            .url(Constant.TOKEN_ENDPOINT)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .post(body)
            .build()

        return request
    }

    fun logout(callback: () -> Unit) {
        val client = OkHttpClient()
        val body = FormBody.Builder()
            .add("client_id", Constant.CLIENT_ID)
            .add("refresh_token", Session.REFRESH_TOKEN) // Send the refresh_token
            .build()
        // Build the request ensuring the content type is set to `application/x-www-form-urlencoded`
        // and the form body
        val request = Request.Builder()
            .url(Constant.LOGOUT_ENDPOINT)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .post(body)
            .build()
        Thread {
            val response = client.newCall(request).execute()
            if (response.body != null) {
                callback()
            }
        }.start()
    }
}