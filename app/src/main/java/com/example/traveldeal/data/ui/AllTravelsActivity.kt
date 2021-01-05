package com.example.traveldeal.data.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldeal.R
import utils.TravelRecyclerViewAdapter

class AllTravelsActivity : AppCompatActivity(),TravelRecyclerViewAdapter.OnItemClickListener {
    private lateinit var model: TravelViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_travels)

        model = ViewModelProvider(this).get(TravelViewModel::class.java)
        //    .also { model = it }
        recyclerView = findViewById(R.id.rvUserTravels)
        /*
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(applicationContext)
        }
*/
        model.getAllTravels().observe(this, {
            if (it != null)
                recyclerView.adapter = TravelRecyclerViewAdapter(it,this)
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.setHasFixedSize(false)
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "item $position clicked", Toast.LENGTH_SHORT).show()
    }
}