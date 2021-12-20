package com.example.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Button
import android.widget.FrameLayout
import com.example.final_project.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    var isBottomSheetLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canvas_test)

        val ca:CanvasAnimation = CanvasAnimation(this)
//        ca.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT,500)

        findViewById<ViewGroup>(R.id.centerLayout).addView(ca)



        var  task:Runnable = Runnable{
            while(true){
                ca.nextFrame()
                Thread.sleep(100)
            }
        }

        var td = Thread(task)
        td.start()

        if(isBottomSheetLoad){
            // Dynamic load bottom sheet
            var inf:LayoutInflater = LayoutInflater.from(this)
            findViewById<ViewGroup>(R.id.MainScreen).addView(inf.inflate(R.layout.bottom_sheet,findViewById<ViewGroup>(R.id.MainScreen),false))
        }

    }
}