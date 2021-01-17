package utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldeal.R
import com.example.traveldeal.data.entities.Travel
import com.example.traveldeal.data.enums.Status
import com.google.android.material.switchmaterial.SwitchMaterial

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return App.instance.getString(stringRes, *formatArgs)
    }
}

class TravelRecyclerViewAdapter(
    private val travelList: List<Travel>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<TravelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_card_travel, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {

        val currentItem = travelList[listPosition]
        holder.itemID = currentItem.clientId
        var tmp = currentItem.departureAddress
        holder.sourceAddress.text =
            if (tmp.indexOf(",") == -1) tmp else tmp.substring(0, tmp.lastIndexOf(","))
        tmp = currentItem.destinationAddress
        holder.destinationAddress.text =
            if (tmp.indexOf(",") == -1) tmp else tmp.substring(0, tmp.lastIndexOf(","))
        holder.departureDate.text = currentItem.departureDate
        holder.returnDate.text = currentItem.returnDate

        holder.companySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                for (offer in currentItem.company.keys)
//                    currentItem.company[offer] = false
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentItem.requestStatus = Status.RUNNING
                for (offer in currentItem.company?.keys!!)
                    currentItem.company?.set(offer, parent?.selectedItem.toString() == offer)
                listener.updateTravel(currentItem)
//                notifyDataSetChanged()
            }
        }
        val aa = travelList[listPosition].company?.keys?.let {
            ArrayAdapter(
                App.instance,
                android.R.layout.simple_spinner_item,
                it.toList()
            )
        }
        // Set layout to use when the list of choices appear
        aa?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        holder.companySpinner.adapter = aa
        holder.switchEnded.isEnabled =
            currentItem.requestStatus == Status.RECEIVED // TODO: 15/01/2021 need to change to RUNNING

        val passengersNum = currentItem.passengersNumber.toString()

        holder.psgNum.text =
            if (passengersNum == "1") {
                Strings.get(R.string.onePassengers)
            } else passengersNum + " ${Strings.get(R.string.passengersNumber)}"

//        holder.expandableLayout.visibility = if (currentItem.expandable) View.VISIBLE else View.GONE
//
//        holder.mainLayout.setOnClickListener {
//            travelList[listPosition].expandable = !travelList[listPosition].expandable
//            notifyItemChanged(listPosition)
//        }
        holder.expandableLayout.visibility = if (currentItem.expandable) View.VISIBLE else View.GONE

        holder.mainLayout.setOnClickListener {
            travelList[listPosition].expandable = !travelList[listPosition].expandable
            notifyItemChanged(listPosition)
        }
//        if (holder.switchEnded.isEnabled) {
//            holder.switchEnded.setOnTouchListener(  object : View.OnTouchListener {
//                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                    when (event?.action) {
//                        MotionEvent.ACTION_DOWN ->
//                            Toast.makeText(holder, "הנסיעה לא פעילה", Toast.LENGTH_SHORT).show()
//                    }
//                    return v?.onTouchEvent(event) ?: true
//                }
//            })
//        }
        holder.switchEnded.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                currentItem.requestStatus = Status.CLOSED

            listener.updateTravel(currentItem)
            notifyDataSetChanged()
        })
    }

    override fun getItemCount() = travelList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)/*,
        View.OnClickListener*/ {
        var itemID: String = ""
        var sourceAddress: TextView = this.itemView.findViewById(R.id.TextViewDepartureAddress)
        var destinationAddress: TextView =
            this.itemView.findViewById(R.id.TextViewDestinationAddress)
        var departureDate: TextView = this.itemView.findViewById(R.id.TextViewDepartureDate)
        var returnDate: TextView = this.itemView.findViewById(R.id.TextViewReturnDate)
        var psgNum: TextView = this.itemView.findViewById(R.id.TextViewPassengersNumber) as TextView
        var expandableLayout: LinearLayout = this.itemView.findViewById(R.id.ExpandableLayout)
        var mainLayout: RelativeLayout = this.itemView.findViewById(R.id.cardMainLayout)
        var companySpinner: Spinner = this.itemView.findViewById(R.id.spinnerOffers)
        var switchEnded: SwitchMaterial = this.itemView.findViewById(R.id.switch_ended)

//        init {
//            itemView.setOnClickListener(this)
//        }

//        override fun onClick(v: View?) {
//            if (adapterPosition != RecyclerView.NO_POSITION) {
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }

    interface OnItemClickListener {
        fun updateTravel(travel: Travel)
    }
}