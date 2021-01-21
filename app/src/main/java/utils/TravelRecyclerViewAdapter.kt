package utils

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldeal.R
import com.example.traveldeal.data.entities.Travel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import utils.Utils.Companion.decodeKey
import utils.Utils.Companion.encodeKey

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return App.instance.getString(stringRes, *formatArgs)
    }
}

/**
 *  RecyclerViewAdapter class for activity allTravels
 */
class TravelRecyclerViewAdapter(
    private val travelList: List<Travel>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<TravelRecyclerViewAdapter.ViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_card_travel, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("RestrictedApi", "SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        holder.travel = travelList[listPosition]
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

        val passengersNum = currentItem.passengersNumber.toString()
        holder.psgNum.text =
            if (passengersNum == "1") {
                Strings.get(R.string.onePassengers)
            } else passengersNum + " ${Strings.get(R.string.passengersNumber)}"

        val spinnerDefaultText = "בחר הצעה"
        //get all the companies key
        val adapterList = currentItem.company.keys.toMutableList()
        // decode all the companies key
        adapterList.replaceAll { decodeKey(it) }
        //keep the spinnerDefaultText in index 0 of the spinner
        val noSelectionIndex = adapterList.indexOf(spinnerDefaultText)
        adapterList.let {
            it[noSelectionIndex] = it[0]
            it[0] = spinnerDefaultText
        }

        val arrayAdapter = ArrayAdapter(
            App.instance,
            android.R.layout.simple_spinner_item,
            adapterList
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set Adapter to Spinner
        holder.companySpinner.adapter = arrayAdapter

        val spinnerKeys = currentItem.company.filter { it.value }.keys
        val index =
            arrayAdapter.getPosition(if (spinnerKeys.isNotEmpty()) spinnerKeys.first() else spinnerDefaultText)
        holder.companySpinner.setSelection(index)


        //the layout expandableLayout is visible only if There are travel offers, and the requestStatus is SENT or RECEIVED
        holder.expandableLayout.visibility =
            if (currentItem.company.size > 1 && currentItem.requestStatus != Status.RUNNING) View.VISIBLE else View.GONE

        //the textView tvNoOffers is visible only if There are no travel offers
        holder.tvNoOffers.visibility =
            if (currentItem.company.size == 1) View.VISIBLE else View.GONE

        //the switch switchEnded is visible only if the requestStatus is RUNNING
        holder.switchEnded.visibility =
            if (currentItem.requestStatus == Status.RUNNING) View.VISIBLE else View.GONE
    }

    override fun getItemCount() = travelList.size

    fun del(position: Int) {
        if (travelList[position].requestStatus != Status.RUNNING)
            listener.deleteTravel(travelList[position])
        else
            Toast.makeText(App.instance, "לא ניתן למחוק נסיעה פעילה", Toast.LENGTH_SHORT).show()
        notifyDataSetChanged()
    }

    /**
     * inner class ViewHolder
     */
    @RequiresApi(Build.VERSION_CODES.N)
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemID: String = ""
        var sourceAddress: TextView = this.itemView.findViewById(R.id.TextViewDepartureAddress)
        var destinationAddress: TextView =
            this.itemView.findViewById(R.id.TextViewDestinationAddress)
        var departureDate: TextView = this.itemView.findViewById(R.id.TextViewDepartureDate)
        var returnDate: TextView = this.itemView.findViewById(R.id.TextViewReturnDate)
        var psgNum: TextView = this.itemView.findViewById(R.id.TextViewPassengersNumber) as TextView
        var expandableLayout: LinearLayout = this.itemView.findViewById(R.id.ExpandableLayout)
        var companySpinner: Spinner = this.itemView.findViewById(R.id.spinnerOffers)
        var switchEnded: SwitchMaterial = this.itemView.findViewById(R.id.switch_ended)
        private var btChoice: Button = this.itemView.findViewById(R.id.bt_spinnerChoice)
        var tvNoOffers: TextView = this.itemView.findViewById(R.id.TextViewNoOffers)
        private val spinnerDefaultText = "בחר הצעה"
        lateinit var travel: Travel

        init {

            //improve button of the spinner selection, and set snakeBar to confirm
            btChoice.setOnClickListener {
                val key = encodeKey(companySpinner.selectedItem.toString())
                for (offer in travel.company.keys)
                    travel.company[offer] = key == offer
                //set the requestStatus to RUNNING
                travel.requestStatus = Status.RUNNING
                listener.updateTravel(travel)
                Toast.makeText(App.instance, R.string.company_selected, Toast.LENGTH_SHORT)
                    .show()
            }

            //Check if the switch switchEnded is on or off, and set snakeBar to confirm the selection
            switchEnded.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener
            { _, isChecked ->
                if (isChecked) {
                    Snackbar.make(switchEnded, R.string.end_travel, 4000)
                        .setAction(R.string.cancel, View.OnClickListener {
                            switchEnded.isChecked = false

                        })
                        .addCallback(object : Snackbar.Callback() {
                            override fun onDismissed(
                                transientBottomBar: Snackbar?,
                                event: Int
                            ) {
                                if (event == DISMISS_EVENT_TIMEOUT) {
                                    //set the requestStatus to CLOSED
                                    travel.requestStatus = Status.CLOSED
                                    listener.updateTravel(travel)
                                    Toast.makeText(
                                        App.instance,
                                        R.string.close_travel_Toast,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    notifyDataSetChanged()
                                }
                            }

                            override fun onShown(sb: Snackbar?) {}
                        }).show()
                }
            })
        }
    }

    interface OnItemClickListener {
        fun updateTravel(travel: Travel)
        fun deleteTravel(travel: Travel)
    }
}