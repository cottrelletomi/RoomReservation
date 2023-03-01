package com.example.roomreservation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomreservation.R
import com.example.roomreservation.model.Reservation

class ReservationAdapter(private val reservations: List<Reservation>, private val onItemClicked: (Reservation) -> Unit) : RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {

    // create new view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.roomName.text = reservation.room.name
        holder.reservationDate.text = reservation.date.toString()
        holder.reservationDuration.text = reservation.duration.toString()
        holder.itemView.setOnClickListener {
            onItemClicked(reservation)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return reservations.size
    }

    // Holds the view for adding it to image and text
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val roomName: TextView = itemView.findViewById(R.id.room_name)
        val reservationDate: TextView = itemView.findViewById(R.id.reservation_date)
        val reservationDuration: TextView = itemView.findViewById(R.id.reservation_duration)
    }
}