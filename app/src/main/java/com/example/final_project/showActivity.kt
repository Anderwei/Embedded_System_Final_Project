package com.example.final_project

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class showActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introduction_layout)

        val textview : TextView = findViewById(R.id.textView2)
        val img : ImageView = findViewById(R.id.imageView2)

        val bundle : Bundle? = intent.extras
        val text = bundle!!.getString("title")
        val image = bundle.getInt("img")

        textview.text = text
        img.setImageResource(image)

    }
}
