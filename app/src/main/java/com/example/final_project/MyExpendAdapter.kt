package com.example.final_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MyExpendAdapter(private val data : ArrayList<NP_Item>) : RecyclerView.Adapter<MyExpendAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyExpendAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.expend_list,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyExpendAdapter.MyViewHolder, position: Int) {
        val currentItem = data[position]
        holder.title.text = currentItem.title
        holder.expendContent.text = currentItem.content

        val isVisible : Boolean = currentItem.visibility
        holder.constraintLayout.visibility = if(isVisible) View.VISIBLE else View.GONE

        holder.title.setOnClickListener{
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.textView3)
        val expendContent : TextView = itemView.findViewById(R.id.textView4)
        val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.expendLayout)
    }
}