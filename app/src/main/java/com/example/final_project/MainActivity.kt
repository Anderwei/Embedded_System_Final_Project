package com.example.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.w3c.dom.Text
import java.util.*


class MainActivity : AppCompatActivity() {

    var isBottomSheetLoad = true

    val test : Array<String> = arrayOf(
        "Path",
        "OIptimize Problem",
        "Data Mining"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isBottomSheetLoad) {

            // Dynamic load bottom sheet
            var inf: LayoutInflater = LayoutInflater.from(this)
            findViewById<ViewGroup>(R.id.MainScreen).addView(inf.inflate(R.layout.bottom_sheet, findViewById<ViewGroup>(R.id.MainScreen), false))

            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.my_list)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = MyAdapter(test)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                override fun onItemClick(position: Int){
                    Toast.makeText(this@MainActivity,test[position],Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}