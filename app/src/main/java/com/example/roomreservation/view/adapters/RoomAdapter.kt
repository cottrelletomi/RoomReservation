package com.example.roomreservation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomreservation.R
import com.example.roomreservation.model.Room

class RoomAdapter(private val rooms: List<Room>, private val onItemClicked: (Room) -> Unit) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    // create new view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = rooms[position]
        holder.roomName.text = room.name
        holder.itemView.setOnClickListener {
            onItemClicked(room)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return rooms.size
    }

    // Holds the view for adding it to image and text
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val roomName: TextView = itemView.findViewById(R.id.room_name)
    }
}