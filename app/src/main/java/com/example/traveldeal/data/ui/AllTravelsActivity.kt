package com.example.traveldeal.data.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldeal.R
import com.example.traveldeal.data.entities.Travel
import com.google.android.material.snackbar.Snackbar
import utils.App
import utils.SwipeToDelete
import utils.TravelRecyclerViewAdapter
import java.time.Duration

/**
 * Activity AllTravels
 * displays all the user travels
 */
class AllTravelsActivity : AppCompatActivity(), TravelRecyclerViewAdapter.OnItemClickListener {
    private lateinit var model: TravelViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var travelsList: MutableList<Travel?>
    lateinit var noTravtlsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_travels)

        model = ViewModelProvider(this).get(TravelViewModel::class.java)
        recyclerView = findViewById(R.id.rvUserTravels)
        noTravtlsTextView = findViewById(R.id.no_travels_textView)


        var adapter: TravelRecyclerViewAdapter
        model.getTravelsByStatus().observe(this, {
            adapter = TravelRecyclerViewAdapter(it as List<Travel>, this)
            recyclerView.adapter = adapter

            ItemTouchHelper(object : SwipeToDelete(this, 0, ItemTouchHelper.LEFT) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    super.onSwiped(viewHolder, direction)
                    adapter.del(viewHolder.adapterPosition)
                }
            }).attachToRecyclerView(recyclerView)

            if (it.isNotEmpty()
            ) {
                travelsList = it as MutableList<Travel?>
                noTravtlsTextView.visibility = View.GONE
            }
            if (it.isEmpty())
                noTravtlsTextView.visibility = View.VISIBLE
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun updateTravel(travel: Travel) {
        model.updateItem(travel)
    }

    override fun deleteTravel(travel: Travel) {
        Snackbar.make(noTravtlsTextView, R.string.delete_confriom,3000)
            .setAction("כן", View.OnClickListener {
                model.deleteItem(travel)
                Toast.makeText(App.instance, R.string.travel_deleted, Toast.LENGTH_SHORT).show()
            })
            .addCallback(object : Snackbar.Callback() {
                override fun onDismissed(
                    transientBottomBar: Snackbar?,
                    event: Int
                ) {
                }
                override fun onShown(sb: Snackbar?) {}
            }).show()
    }

}