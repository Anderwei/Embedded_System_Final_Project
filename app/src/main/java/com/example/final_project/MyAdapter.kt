package com.example.final_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MyAdapter(private val data: ArrayList<Item>) :  RecyclerView.Adapter<MyViewHolder>(){

    private  lateinit var mylistener: onItemClickListener

    interface onItemClickListener{
            fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mylistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
        )

        return MyViewHolder(itemView,mylistener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItems = data[position % data.size]
        holder.textView.text = currentItems.t
//        holder.textView.background = currentItems.img
        holder.imageView.setImageResource(currentItems.img)
    }

    override fun getItemCount(): Int {
        return 1000
    }

}

class MyViewHolder(itemView: View, listener: MyAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView){

    var textView: TextView = itemView.findViewById(R.id.textView1)
    var imageView : ImageView = itemView.findViewById(R.id.imageView)

    init{
//        textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        itemView.setOnClickListener{
            listener.onItemClick(adapterPosition)
        }
    }
}
