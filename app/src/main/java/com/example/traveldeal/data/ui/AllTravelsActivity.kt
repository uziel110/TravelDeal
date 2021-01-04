package com.example.traveldeal.data.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldeal.R
import utils.TravelRecyclerViewAdapter

class AllTravelsActivity : AppCompatActivity() {
    lateinit var model: TravelViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_travels)

        model = ViewModelProvider(this).get(TravelViewModel::class.java)
            .also { model = it }
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(applicationContext)
        }
        model.getAllTravels().observe(this, {
            if (it != null)
                recyclerView.adapter = TravelRecyclerViewAdapter(it)
        })
    }
}