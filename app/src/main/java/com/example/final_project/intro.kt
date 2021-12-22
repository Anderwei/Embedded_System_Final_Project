package com.example.final_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

data  class Item(var img: Int,var t : String) {}

class intro : AppCompatActivity(){
    var isBottomSheetLoad = true
    lateinit var OptionImg : Array<Int>
    lateinit var OptionText : Array<String>
    lateinit var testList : ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_layout)
        OptionImg = arrayOf(
                R.drawable.pathfinding,
                R.drawable.optimization,
                R.drawable.datamining
        )

        OptionText = arrayOf(
                "PathFinding",
                "Optimization",
                "DataMining"
        )

        testList = arrayListOf<Item>()

        for(i in 0..2){
            val temp = Item(OptionImg[i],OptionText[i])
            testList.add(temp)
        }

        if (isBottomSheetLoad) {

            // Dynamic load bottom sheet
            var inf: LayoutInflater = LayoutInflater.from(this)
            findViewById<ViewGroup>(R.id.intro_Screen).addView(inf.inflate(R.layout.bottom_sheet, findViewById<ViewGroup>(R.id.intro_Screen), false))

            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.my_list)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = MyAdapter(testList)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                override fun onItemClick(position: Int){
                    val intent = Intent(this@intro,showActivity::class.java)
                    intent.putExtra("title",testList[position % 3].t)
                    intent.putExtra("img" ,testList[position % 3].img)
                    startActivity(intent)
                }
            })
        }
    }

//    override fun onStart() {
//        super.onStart()
//        overridePendingTransition(R.anim.main_out,R.anim.intro_start)
//    }
}