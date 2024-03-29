package com.example.traveldeal.data.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.traveldeal.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import utils.MyService
import utils.Strings

/**
 * main activity
 */
class MainActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 123
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, MyService::class.java))

        sharedPreferences = getSharedPreferences("name", MODE_PRIVATE)

        if (sharedPreferences.getBoolean("user", false))
            return
        startSignInIntent()
    }

    //Sign-In fire base intent
    private fun startSignInIntent() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )
        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setLogo(R.drawable.app_logo)
                .build(),
            RC_SIGN_IN
        )
        // [END auth_fui_create_intent]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    sharedPreferences.edit().putBoolean("user", true).apply()
                    Toast.makeText(
                        this,
                        "${Strings.get(R.string.welcome)} " + user.displayName,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "Sign in failed", Toast.LENGTH_LONG).show()
                startSignInIntent()
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    /**
     * Called when the user taps on the Add Travel button
     */
    fun startTravelButton(view: View) {
        val intent = Intent(this, AddTravelActivity::class.java)
        this.startActivity(intent)
    }

    /**
     * Called when the user taps on the My Travels button
     */
    fun btMyTravels(view: View) {
        val intent = Intent(this, AllTravelsActivity::class.java)
        this.startActivity(intent)
    }

    /**
     * Called when the user taps on the sign-out button
     */
    fun signOutButton(view: View) {
        FirebaseAuth.getInstance().signOut()
        startSignInIntent()
    }
}