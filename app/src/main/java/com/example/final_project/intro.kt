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
                R.drawable.polynomial_time,
                R.drawable.decisionproblem,
                R.drawable.np_graph,
                R.drawable.np_hard,
                R.drawable.pathfinding,
                R.drawable.optimization,
                R.drawable.datamining,
                R.drawable.circuit
        )

        OptionText = arrayOf(
                "Polynomial Time",
                "Decision Problem",
                "What is NP?",
                "What is NP-Hard / NP-Complete",
                "PathFinding",
                "Optimization",
                "DataMining",
                "Circuit"
        )

        npListContent = arrayOf(
                //Polynomial Time
                "Polynomial Time:\n" +
                "多項式時間（英語：Polynomial time）在計算複雜度理論中，指的是一個問題的計算時間m(n)不大於問題大小n的多項式倍數。\n" +
                "以數學描述的話，則可說m(n)= O (n^k)，k為一常數值\n",

                //Decision Problem
                "決策問題:\n" +
                "一個決策問題(decision problem)是指其輸出，只有「是」或「否」的問題。例如，搜尋問題為詢問 x 是否出現在一個集合 A 中?若有則輸出「是」，否則輸出「否」。\n",

                //What is NP
                "存在著一些問題，人類目前尚無法將他們歸入集合 P 中。為了思考這些問題，於是在一般演算法可採用的功能上，擴增以下虛構的新指令。這些新指令雖然不存在於現實中，但是對探討這些難題的性質及彼此的關係，有很大的幫助。以下是這些虛構的新指令：\n" +
                "1. choice(S)：自集合 S 中，選出會導致正確解的一個元素。當集合 S 中無此元素時，則可任意選擇一個元素。\n" +
                "2. failure()：代表失敗結束。\n" +
                "3. success()：代表成功結束。\n",

                //NP-Hard / NP-Complete
                "NP困難（NP-hardness / non-deterministic polynomial-time hardness）\n" +
                "此問題是計算複雜性理論中最重要的複雜性類別之一。如果所有NP問題都可以多項式時間歸約到某個問題，則稱該問題為NP困難。\n" +
                "NP完全(NP-Complete)\n" +
                "NP-Complete為NP與NP困難的交集，是NP中最難的決策問題，所有NP問題都可以被快速歸化為NP完備問題。因此NP完備問題應該是最不可能被化簡為P的決策問題的集合。若任何NPC問題得到多項式時間的解法，那此解法就可應用在所有NP問題上。\n" +
                "NP-algorithm:\n" +
                        "Algorithm satisfiability (E (x 1, … , xn ))\n" +
                        "{ Step 1: for i =1 to n do\n" +
                        "xi ←choice (true, false) \n" +
                        "Step 2: if E (x 1, … , x n) is true then success ()\n" +
                        "　　　 else failure ();\n" +
                        "}\n",
                "",
                "",
                "",
                ""
        )

        dataList = arrayListOf<Item>()
        npList = arrayListOf<NP_Item>()

        for (i in 0..7) {
            val temp = Item(OptionImg[i], OptionText[i])
            val temp2 = NP_Item(OptionText[i], npListContent[i])
            dataList.add(temp)
            npList.add(temp2)
        }

    }
}