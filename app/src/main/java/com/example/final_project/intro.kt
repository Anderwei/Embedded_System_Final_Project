package com.example.final_project

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

data class Item(var img: Int,var t : String) {}
data class NP_Item(var title: String, var content :String, var visibility: Boolean = false){}

class intro : AppCompatActivity() {
    var isBottomSheetLoad = true
    lateinit var OptionImg: Array<Int>
    lateinit var OptionText: Array<String>
    lateinit var dataList: ArrayList<Item>
    lateinit var npListTitle : Array<String>
    lateinit var npListContent : Array<String>
    lateinit var npList: ArrayList<NP_Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_layout)

        init()

        if (isBottomSheetLoad) {

            // Dynamic load bottom sheet
            var inf: LayoutInflater = LayoutInflater.from(this)
            findViewById<ViewGroup>(R.id.intro_Screen).addView(inf.inflate(R.layout.bottom_sheet, findViewById<ViewGroup>(R.id.intro_Screen), false))

            //np explaination
            val recyclerViewNP : RecyclerView = findViewById<RecyclerView>(R.id.recyclerView1)
            recyclerViewNP.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            val adapterNP = MyExpendAdapter(npList)
            recyclerViewNP.adapter = adapterNP

            //animation option
            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.my_list)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = MyAdapter(dataList)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@intro, showActivity::class.java)
                    intent.putExtra("title", dataList[position % 4].t)
                    intent.putExtra("img", dataList[position % 4].img)
                    startActivity(intent)
                }
            })
        }
    }

    private fun init() {
        //activity change animation
        val fadeTransition = Fade()
        fadeTransition.duration = 1500
        window.enterTransition = fadeTransition
        window.exitTransition = fadeTransition

        //initial option text
        OptionImg = arrayOf(
                R.drawable.pathfinding,
                R.drawable.optimization,
                R.drawable.datamining,
                R.drawable.circuit
        )

        OptionText = arrayOf(
                "PathFinding",
                "Optimization",
                "DataMining",
                "Circuit"
        )

        npListTitle = arrayOf(
                "What is NP ?",
                "What is NP-Complete / NP-Hard ?"
        )

        npListContent = arrayOf(
                "",
                ""
        )

        dataList = arrayListOf<Item>()
        npList = arrayListOf<NP_Item>()

        for (i in 0..3) {
            val temp = Item(OptionImg[i], OptionText[i])
            val temp2 = NP_Item(OptionText[i], npListContent[i])
            dataList.add(temp)
            npList.add(temp2)
        }

    }
}