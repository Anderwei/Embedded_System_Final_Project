package com.example.final_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun startIntro(view: View){
        val introActivity : Intent = Intent(this,intro::class.java)
        startActivity(introActivity)
    }

    //    override fun onStart() {
    //        super.onStart()
    //        overridePendingTransition(R.anim.main_out,R.anim.intro_start)
    //    }
}