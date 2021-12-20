package com.example.final_project

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MyAdapter(private val data: Array<String>) :  RecyclerView.Adapter<MyViewHolder>(){

    private  lateinit var mylistener: onItemClickListener

    interface onItemClickListener{
            fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mylistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.recycleview_t,
                parent,
                false
        )

        return MyViewHolder(itemView,mylistener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItems = data[position]
        holder.setData(currentItems)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

class MyViewHolder(itemView: View, listener: MyAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView){

    var textView: TextView = itemView.findViewById(R.id.textView)

    init{
        textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        itemView.setOnClickListener{
            listener.onItemClick(adapterPosition)
        }
    }

    fun setData(data: String){
        textView.text = data.toString()
    }
}
