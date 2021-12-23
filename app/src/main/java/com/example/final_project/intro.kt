package com.example.final_project

import android.graphics.Color
import android.os.Bundle
import android.transition.Fade
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetBehavior


data class Item(var img: Int,var t : String) {}
data class NP_Item(var title: String, var content :String, var visibility: Boolean = false){}

class intro : AppCompatActivity() {
    var isBottomSheetLoad = true
    lateinit var OptionImg: Array<Int>
    lateinit var OptionText: Array<String>
    lateinit var dataList: ArrayList<Item>
    lateinit var NpListTitle:Array<String>
    lateinit var npListContent : Array<String>
    lateinit var problemContent:Array<String>
    lateinit var npList: ArrayList<NP_Item>

    lateinit var bottom_sheet_behavior:BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_layout)

        init()

        if (isBottomSheetLoad) {


            // Dynamic load bottom sheet
            val inf: LayoutInflater = LayoutInflater.from(this)
            findViewById<ViewGroup>(R.id.intro_Screen).addView(inf.inflate(R.layout.bottom_sheet, findViewById(R.id.intro_Screen), false))



            //animation option
            val recyclerView: RecyclerView = findViewById(R.id.my_list)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = MyAdapter(dataList)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    findViewById<LinearLayout>(R.id.constraintLayout).removeAllViews()

                    if(position % 5 == 0){
                        val rv = RecyclerView(applicationContext)
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(rv,0)
                        rv.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
                        val adapterNP = MyExpendAdapter(npList)
                        rv.adapter = adapterNP
                    }

                    if(position % 5 == 1){
                        val ca = CanvasAnimation(applicationContext,0)
                        ca.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(400 * applicationContext.resources.displayMetrics.density).toInt())
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(ca)

                        val tv = TextView(applicationContext)
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(tv)
                        tv.text = problemContent[0]
                        tv.setTextColor(Color.rgb(255,255,255))
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,(18).toFloat())
                    }

                    if(position % 5 == 2){
                        val ca = CanvasAnimation(applicationContext,1)
                        ca.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(400 * applicationContext.resources.displayMetrics.density).toInt())
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(ca)

                        val tv = TextView(applicationContext)
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(tv)
                        tv.text = problemContent[1]
                        tv.setTextColor(Color.rgb(255,255,255))
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,(18).toFloat())
                    }

                    if(position % 5 == 3){
                        val ca = CanvasAnimation(applicationContext,2)
                        ca.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(400 * applicationContext.resources.displayMetrics.density).toInt())
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(ca)

                        val tv = TextView(applicationContext)
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(tv)
                        tv.text = problemContent[2]
                        tv.setTextColor(Color.rgb(255,255,255))
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,(18).toFloat())
                    }

                    if(position % 5 == 4){
                        val gif =  GifImageView(applicationContext);
                        gif.setImageResource(R.drawable.chess)
                        gif.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(400 * applicationContext.resources.displayMetrics.density).toInt())
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(gif)

                        val tv = TextView(applicationContext)
                        findViewById<LinearLayout>(R.id.constraintLayout).addView(tv)
                        tv.text = problemContent[3]
                        tv.setTextColor(Color.rgb(255,255,255))
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,(18).toFloat())
                    }

                }
            })


            bottom_sheet_behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))

            bottom_sheet_behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if( newState == BottomSheetBehavior.STATE_EXPANDED){
                        findViewById<ImageView>(R.id.imageView3).animate().rotation((180).toFloat()).start()
                    }else{
                        findViewById<ImageView>(R.id.imageView3).animate().rotation((0).toFloat()).start()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
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
                R.drawable.circuit,
                R.drawable.optimization
        )

        OptionText = arrayOf(
                "Introduction of NP / NP-Hard",
                "PathFinding - NP",
                "PathFinding - NP Hardness",
                "Circuit",
                "Chess",
        )

        NpListTitle = arrayOf(
                "多項式時間(Polynomial time)",
                "決策問題(decision problem)",
                "什麼是NP / NP-Complete?",
                "什麼是 NP-Hard?",
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
        )

        problemContent = arrayOf(
                    "這個問題是在於要找一個不重複的路徑能走過所有圖上的點\n" +
                    "對於這個問題沒有一個快速的解法\n" +
                    "唯一的方法便是嘗試所有可能的路徑\n" +
                    "如動畫中的藍色線顯示的\n" +
                    "但要驗證是不是合法的 Hamilton Path 很簡單\n" +
                    "只要走一遍路徑並檢查有沒有通過所有點\n" +
                    "便能直接的確認是不是 Hamilton Path\n" +
                    "因為有一個快速驗證的方法\n" +
                    "因此這個問題是一個 NP 問題",

                    "這個問題雖然看似跟非最短路徑版類似\n" +
                    "但在求解與驗算部份卻天差地遠\n" +
                    "找到最佳路徑依然還是得靠嘗試\n" +
                    "不一樣的是無法透過純粹的檢查路徑確認是否為最短\n" +
                    "因此仍得像找最佳路徑般尋遍所有可能的走法\n" +
                    "因為驗證的時間跟尋找解的時間都是一樣的\n" +
                    "且不是多項式時間內能解出的問題\n" +
                    "所以被歸在 NP hard 的部份",

                    "這個問題是在給予一張電路圖\n" +
                    "找出是否有輸入能使這組電路輸出 1 的訊號\n" +
                    "這個問題除了直接測試所有可能的組合外\n" +
                    "已有許多的演算法可以有效率的處理這個問題\n" +
                    "此問題也是最早被證明為 NP-Complete 的問題",

                    "西洋棋作為一個古老的棋盤遊戲\n" +
                    "自古便有許多人希望能找到一個必勝的路徑\n" +
                    "因此便衍生出了一個著名的NP-Hard問題\n" +
                    "「對於現有的棋面是否存在著一個步驟，使黑/白方必勝」\n" +
                    "近年來雖然透過許多方法逼近最佳解\n" +
                    "但對於這個題目最直接的解\n" +
                    "仍是世紀上的一大難題"
        )

        dataList = arrayListOf()
        npList = arrayListOf()

        for(i in NpListTitle.indices) {
            npList.add(NP_Item(NpListTitle[i], npListContent[i]))
        }

        for (i in OptionText.indices) {
            dataList.add(Item(OptionImg[i], OptionText[i]))
        }

    }

    fun openBottomSheet(view: View){
        if(bottom_sheet_behavior.state == BottomSheetBehavior.STATE_COLLAPSED){
            bottom_sheet_behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }else{
            bottom_sheet_behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}