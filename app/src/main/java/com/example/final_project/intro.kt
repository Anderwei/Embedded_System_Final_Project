package com.example.final_project

import android.content.Intent
import android.os.Bundle
import android.transition.Explode
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
//                    val intent = Intent(this@intro, showActivity::class.java)
//                    intent.putExtra("title", dataList[position % 4].t)
//                    intent.putExtra("img", dataList[position % 4].img)
//                    startActivity(intent)
                    

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
                R.drawable.np_graph,
                R.drawable.pathfinding,
                R.drawable.ptsd,
                R.drawable.optimization,
                R.drawable.circuit
        )

        OptionText = arrayOf(
                "Introduction of NP / NP-Hard",
                "PathFinding - NP",
                "PathFinding - NP Hardness",
                "Optimization",
                "Circuit"
        )

        npListContent = arrayOf(
                //Polynomial Time
                "多項式時間(Polynomial time):\n" +
                        "指的是對於一個演算法對於長為n的資料時\n" +
                        "所需的計算時間為一個多項式\n" +
                        "相較於指數性成長的問題，所需時間多半是可接受的",

                //Decision Problem
                "決策問題(decision problem):\n" +
                        "決策問題是指問題的解答只有是與不是兩種\n" +
                        "例如「10 是否是質數?」這個問題只有是與不是兩種",

                //What is NP
                "“Non-deterministic Polynomial Problem”\n" +
                        "指的是需要Polynomial time計算且在過程中會發生分支的問題\n" +
                        "如多數的尋路問題，求解的過程中有許多不同的方法\n" +
                        "但最後還是能找到相同或類似的解\n" +
                        "另外因為目前的計算裝置的計算是線性的\n" +
                        "需要先將會分支的問題轉換成不會分支的問題再計算\n" +
                        "因此會直接將計算所需的時間拉長成指數級別\n" +
                        "使多半 NP 問題在現有裝置上所需的計算時間非常長\n" +
                "而許多 NP 問題能轉換成其他較廣義的 NP 問題\n" +
                "這些較廣義的問題就被稱為 NP-Complete\n",

                //NP-Hard / NP-Complete
                        "有些問題雖然也是在計算中會分支\n" +
                        "但其在驗證問題時無法像普通的 NP 問題快速的驗證\n" +
                        "如西洋棋中存在最佳解的問題\n" +
                        "除了計算外，連驗證都需要大量的時間\n" +
                        "這類的問題就會被歸類到 NP-Hard 中",
                "",
                "",
                "",
                ""
        )

        dataList = arrayListOf<Item>()
        npList = arrayListOf<NP_Item>()

        for (i in 0..(OptionText.size - 1)) {
            val temp = Item(OptionImg[i], OptionText[i])
            val temp2 = NP_Item(OptionText[i], npListContent[i])
            dataList.add(temp)
            npList.add(temp2)
        }

    }
}