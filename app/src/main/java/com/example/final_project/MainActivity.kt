package com.example.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    var isBottomSheetLoad = true

    val test = arrayOf(1,2,3,4,5,6,7,8,9,10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        class MyViewHolder(itemView: View/*, private val listener: MyAdapter.OnItemClickListener?*/) : RecyclerView.ViewHolder(itemView){
            public var textView: TextView = itemView.findViewById(R.id.textView)

            fun setData(data: Int){
                textView.text = data.toString()
//                itemView.setOnClickListener {
//                    listner?.onItemClick(data)
//                }
            }
        }

        class MyAdapter :  RecyclerView.Adapter<MyViewHolder>(){
//            var clickListener: AdapterView.OnItemClickListener? = null
//
//            fun setListener(listener: AdapterView.OnItemClickListener?) {
//                this.clickListener = listener
//            }
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                return MyViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.bottom_sheet,
                                parent,
                                false
                        ) //, listener = clickListener
                )
            }
            override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                holder.setData(test[position])
            }
            override fun getItemCount(): Int {
                return test.size
            }
        }


        if(isBottomSheetLoad){

            // Dynamic load bottom sheet
            var inf:LayoutInflater = LayoutInflater.from(this)
            findViewById<ViewGroup>(R.id.MainScreen).addView(inf.inflate(R.layout.bottom_sheet,findViewById<ViewGroup>(R.id.MainScreen),false))

            val recyclerView:RecyclerView = findViewById<RecyclerView>(R.id.my_list)
            recyclerView.adapter = MyAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//
//            val myAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test)
//            var my_list = findViewById<ListView>(R.id.my_list)
//            my_list.adapter = myAdapter
        }
    }
}