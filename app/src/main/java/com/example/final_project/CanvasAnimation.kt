package com.example.final_project
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View

class CanvasAnimation(context: Context) : View(context) {

    private var canvas_width:Int = 0
    private var canvas_height:Int = 0

    private var circular_w_ratio = 0.5
    private var circular_h_ratio = 0.5

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvas_height = h
        canvas_width = w
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas!= null){

            val p = Paint()
            p.strokeWidth = 3f
            p.color = Color.RED
            p.style = Paint.Style.STROKE

            canvas.drawCircle(ratioToSizeW(circular_w_ratio), ratioToSizeH(circular_h_ratio), 150f, p)
        }
    }

    private fun ratioToSizeH(Ratio: Double):Float{
        var ratio:Double = Ratio
        if(ratio <0){
            ratio = 0.0
        }
        if(ratio > 1){
            ratio = 1.0
        }
        return (ratio * canvas_height.toDouble()).toFloat()
    }

    private fun ratioToSizeW(Ratio: Double):Float{
        var ratio:Double = Ratio
        if(ratio <0){
            ratio = 0.0
        }
        if(ratio > 1){
            ratio = 1.0
        }
        return (ratio * canvas_width.toDouble()).toFloat()
    }

    fun nextFrame(){
        this.circular_w_ratio += 0.01
        invalidate()
    }
}
