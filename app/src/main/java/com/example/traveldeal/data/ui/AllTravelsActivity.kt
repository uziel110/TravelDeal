package com.example.traveldeal.data.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
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
    lateinit var noTravtlsTextView: TextView

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
        noTravtlsTextView = findViewById(R.id.no_travels_textView)

        model.getTravelsByStatus()?.observe(this, {

            recyclerView.adapter = TravelRecyclerViewAdapter(it as List<Travel>, this)
            if (it != null && it.isNotEmpty()
            ) {
                travelsList = it as MutableList<Travel?>
                noTravtlsTextView.visibility = View.GONE
            }
            if (it!!.isEmpty())
                noTravtlsTextView.visibility = View.VISIBLE
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun updateTravel(travel: Travel) {
        model.updateItem(travel)
    }

    override fun updateTravel(travel: Travel) {
        model.updateItem(travel)
    }

//    override fun onItemClick(itemID: Int) {
//        val t = travelsList[itemID]
//        Toast.makeText(this, "clientId: ${t!!.clientId}", Toast.LENGTH_SHORT).show()
//    }
}