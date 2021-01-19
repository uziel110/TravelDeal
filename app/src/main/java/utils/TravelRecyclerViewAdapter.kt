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
import com.example.traveldeal.data.enums.Status
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import utils.Utils.Companion.decodeKey
import utils.Utils.Companion.encodeKey

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

    @RequiresApi(Build.VERSION_CODES.N)
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

        val spinnerDefaultText = "בחר הצעה"
        val adapterList = travelList[listPosition].company.keys.toMutableList()
        adapterList.replaceAll { decodeKey(it) }
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
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set Adapter to Spinner
        holder.companySpinner.adapter = arrayAdapter
        fun setSpinner() {
            val spinnerKeys = currentItem.company.filter { it.value }.keys
            val index =
                arrayAdapter.getPosition(if (spinnerKeys.isNotEmpty()) spinnerKeys.first() else spinnerDefaultText)
            holder.companySpinner.setSelection(index)
        }
        setSpinner()

        holder.btChoice.setOnClickListener {
            if (holder.companySpinner.selectedItem.toString() != spinnerDefaultText) {
                for (offer in currentItem.company.keys)
                    currentItem.company[offer] =
                        encodeKey(holder.companySpinner.selectedItem.toString()) == offer
                currentItem.requestStatus = Status.RUNNING
            } else {
                for (offer in currentItem.company.keys)
                    currentItem.company[offer] = false
                currentItem.requestStatus = Status.RECEIVED
            }
            listener.updateTravel(currentItem)
            setSpinner()
        }

        holder.switchEnded.visibility =
            if (currentItem.requestStatus == Status.RUNNING) View.VISIBLE else View.GONE


        val passengersNum = currentItem.passengersNumber.toString()

        holder.psgNum.text =
            if (passengersNum == "1") {
                Strings.get(R.string.onePassengers)
            } else passengersNum + " ${Strings.get(R.string.passengersNumber)}"

//        holder.expandableLayout.visibility = if (currentItem.expandable) View.VISIBLE else View.GONE
        holder.expandableLayout.visibility =
            if (currentItem.company.size > 1 && currentItem.requestStatus != Status.RUNNING) View.VISIBLE else View.GONE
        holder.tvNoOffers.visibility =
            if (currentItem.company.size == 1) View.VISIBLE else View.GONE

        holder.switchEnded.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                var snackbar = Snackbar.make(holder.switchEnded, "הנסיעה אושרה", Snackbar.LENGTH_LONG)
                snackbar.setAction("בטל", View.OnClickListener {
                        holder.switchEnded.isChecked = false
                    })
                snackbar.show()

                if(snackbar.isShownOrQueued() == false){
                    currentItem.requestStatus = Status.CLOSED

                    listener.updateTravel(currentItem)
                    notifyDataSetChanged()
                }



            }

//                Snackbar.make(holder.switchEnded, "Text label", Snackbar.LENGTH_LONG)
//           .setAction("R.string.undo_string", MyUndoListener())
//            .show()

        })


    }

    override fun getItemCount() = travelList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        var btChoice: Button = this.itemView.findViewById(R.id.bt_spinnerChoice)
        var tvNoOffers: TextView = this.itemView.findViewById(R.id.TextViewNoOffers)
    }

    interface OnItemClickListener {
        fun updateTravel(travel: Travel)
    }
}