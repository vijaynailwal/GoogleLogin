package com.example.googlelogin

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_facebook_login.*
import org.json.JSONException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class FacebookLoginActivity : AppCompatActivity() {
    var callbackManager: CallbackManager? = null
    var TAG = "FacebookLoginActivity "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_login)
        keyHash()


        //logout app
        /*
        val loggedOut = AccessToken.getCurrentAccessToken() == null
        if (!loggedOut) {
            Log.e(TAG, "Username is: " + Profile.getCurrentProfile().name)
            getUserProfile(AccessToken.getCurrentAccessToken())
        }*/


//

        login_button.setReadPermissions(listOf("email", "public_profile"))
        callbackManager = CallbackManager.Factory.create()
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                val loggedIn = AccessToken.getCurrentAccessToken() == null
                Log.e(TAG, "$loggedIn ??")


                getUserProfile(AccessToken.getCurrentAccessToken())
            }

            override fun onCancel() {}
            override fun onError(exception: FacebookException) { // App code
            }
        })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getUserProfile(currentAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            currentAccessToken
        ) { `object`, response ->
            Log.e(TAG, "getdata $`object`")
            try {
                val first_name = `object`.getString("first_name")
                val last_name = `object`.getString("last_name")
                Log.e(TAG, "first_name--> $first_name")
                Log.e(TAG, "last_name--> $last_name")

                val email = `object`.getString("email")
                val id = `object`.getString("id")
                val image_url = "https://graph.facebook.com/$id/picture?type=normal"
                Log.e(TAG, image_url)
                Log.e(TAG, id)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "first_name,last_name,email,id")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun keyHash() {
        try {
            val info = packageManager.getPackageInfo(
                "com.example.googlelogin",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                Log.e(
                    "KEY_HASH:",
                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }
}