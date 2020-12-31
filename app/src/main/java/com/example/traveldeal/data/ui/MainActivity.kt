package com.example.traveldeal.data.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.traveldeal.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this, AddTravelActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }, 500)
    }

    /** Called when the user taps the Send button */
    fun startTravelButton(view: View) {
        // TODO: 28-Nov-20 delete
    }
}