package com.example.cars.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.R
import com.example.cars.model.Cars

class CarRecyclerViewAdapter(
    private val car: Cars,
) : RecyclerView.Adapter<CarRecyclerViewAdapter.CarViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_item, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.numberText.text = car.cars[position].number.toString()
        holder.dateText.text = car.cars[position].date
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(
                car.cars[position].number.toString(),
                car.cars[position].date,
                car.cars[position].state,
            )
        }
        selectColor(car.cars[position].state, holder.itemView)
    }

    override fun getItemCount(): Int = car.cars.size

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    private fun selectColor(state: String, view: View){
        when (state) {
            "available" -> {
                view.setBackgroundColor(Color.GREEN)
            }
            "hidden" -> {
                view.setBackgroundColor(Color.parseColor("#aeedeb"))
            }
            else -> view.setBackgroundColor(Color.YELLOW)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(number: String, status: String, date: String)
    }

    class CarViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView){

        val numberText: TextView = itemView.findViewById(R.id.spinnerText)
        val dateText: TextView = itemView.findViewById(R.id.dateText)
    }
}