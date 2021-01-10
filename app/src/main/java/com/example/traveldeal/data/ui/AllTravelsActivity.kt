package com.example.traveldeal.data.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldeal.R
import com.example.traveldeal.data.entities.Travel
import utils.TravelRecyclerViewAdapter

class AllTravelsActivity : AppCompatActivity(), TravelRecyclerViewAdapter.OnItemClickListener {
    private lateinit var model: TravelViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var travelsList: MutableList<Travel?>

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
        val v = model.getAllTravels()
        v.observe(this, {
            if (it != null) {
                recyclerView.adapter = TravelRecyclerViewAdapter(it as List<Travel>, this)
                travelsList = it as MutableList<Travel?>
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.setHasFixedSize(false)
    }

    override fun onItemClick(itemID: Int) {
        val t = travelsList[itemID]
        Toast.makeText(this, "clientId: ${t!!.clientId}", Toast.LENGTH_SHORT).show()
    }
}