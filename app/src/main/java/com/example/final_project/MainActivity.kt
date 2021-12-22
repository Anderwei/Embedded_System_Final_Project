package com.example.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {

    var isBottomSheetLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(isBottomSheetLoad){
            // Dynamic load bottom sheet
            var inf:LayoutInflater = LayoutInflater.from(this)
            findViewById<ViewGroup>(R.id.MainScreen).addView(inf.inflate(R.layout.bottom_sheet,findViewById<ViewGroup>(R.id.MainScreen),false))
        }

    }
}