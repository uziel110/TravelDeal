package utils

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldeal.R
import com.example.traveldeal.data.entities.Travel
import com.firebase.ui.auth.AuthUI

class TravelRecyclerViewAdapter(private val travelList: List<Travel>) :
    RecyclerView.Adapter<TravelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rvUserTravels, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        val currentItem = travelList[listPosition]
        holder.sourceAddress.text = currentItem.departureAddress
        holder.destinationAddress.text = currentItem.destinationAddress
        holder.departureDate.text = currentItem.departureDate
        holder.returnDate.text = currentItem.returnDate
        holder.returnDate.text = currentItem.returnDate
        /*
        val source = holder.sourceAddress
        val destination = holder.destinationAddress
        val date = holder.departureDate
        //val confirm = holder.
        val company = holder.company
        holder.travel = travelList[listPosition]
        source.text = travelList[listPosition].sourceAdders
        destination.text = travelList[listPosition].destinationAddress[0]
        date.text = travelList[listPosition].departureDate
        company.adapter = ArrayAdapter<String>(
            AuthUI.getApplicationContext(),
            android.R.layout.simple_list_item_1,
            travelList[listPosition].serviceProvider.keys.toMutableList()
        )
        */
    }

    override fun getItemCount() = travelList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sourceAddress: TextView =
            this.itemView.findViewById(R.id.editTextTextDepartureAddress) as TextView
        var destinationAddress: TextView =
            this.itemView.findViewById(R.id.editTextTextDestinationAddress) as TextView
        var departureDate: TextView =
            this.itemView.findViewById(R.id.editTextDepartureDate) as TextView
        var returnDate: TextView = this.itemView.findViewById(R.id.editTextReturnDate) as TextView
        //var reqStatus: Button = this.itemView.findViewById(R.id.)
        /*
        var bRunning: Button
        var bFinished: Button
        var company: Spinner
        lateinit var travel: Travel

        init {

            reqStatus.setOnClickListener {
                this@ViewHolder.travel
                travel.status = Status.RECEIVED
            }
            bRunning = this.itemView.findViewById(R.id.bRunning)
            bRunning.setOnClickListener {
                this@ViewHolder.travel
                travel.status = Status.RUNNING
            }
            bFinished = this.itemView.findViewById(R.id.bFinished)
            bFinished.setOnClickListener {
                this@ViewHolder.travel
                travel.status = Status.CLOSED
            }
            company = this.itemView.findViewById(R.id.sCompany) as Spinner
            company.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        bFinished.isEnabled = false
                        bRunning.isEnabled = false
                        reqStatus.isEnabled = false
                        return
                    }
                    this@ViewHolder.travel
                    travel.serviceProvider[parentView?.getItemIdAtPosition(position)
                        .toString()] to true
                    bFinished.isEnabled = true
                    bRunning.isEnabled = true
                    reqStatus.isEnabled = true
                    Log.i("a", "a")
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }
            }
        }
*/

    }
}