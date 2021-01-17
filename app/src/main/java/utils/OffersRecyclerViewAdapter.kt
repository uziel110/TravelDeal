package utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldeal.R
import com.example.traveldeal.data.entities.Travel

class OffersRecyclerViewAdapter(
    private val offersList: List<Travel>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.offers_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {

        val currentItem = offersList[listPosition]
        var tmp = currentItem.departureAddress
        holder.TVOfferName.text = "currentItem.company[listPosition]"
            if (tmp.indexOf(",") == -1) tmp else tmp.substring(0, tmp.lastIndexOf(","))

//        holder.CBofferApproved.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
//            val currOffer = offersList[listPosition]
//            val companyId = FirebaseAuth.getInstance().currentUser?.uid
//
//            listener.updateTravel(currTravel)
//            notifyDataSetChanged()
//        })
//            offersList[listPosition].expandable = !offersList[listPosition].expandable
//            notifyItemChanged(listPosition)
//        }
    }

    override fun getItemCount() = offersList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var TVOfferName: TextView = this.itemView.findViewById(R.id.TextViewOfferName)
        var CBofferApproved: CheckBox = this.itemView.findViewById(R.id.CheckBoxOfferApproved)
    }

    interface OnItemClickListener {
        fun onItemClick(itemID: Int)
    }
}