package com.example.final_project
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import android.view.View
import java.lang.Integer.min


val HAMILTON_PATH = 0
val HAMILTON_SHORTEST_PATH = 1
val CIRCUIT_PROBLEM = 2


class CanvasAnimation(context: Context,algNum : Int) : View(context) {



    private var canvas_width:Int = 0
    private var canvas_height:Int = 0
    private var currentAlg:Int = algNum

    private var algs:ArrayList<AlgorithmHandler> = arrayListOf(HamiltonPathAlgHandler(),HamiltonShortestPathHandler(),CircuitProblemHandler())


    init {
        val task = Runnable{
            while(true){
                invalidate()
                Thread.sleep(100)
            }
        }

        val td = Thread(task)
        td.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvas_height = h
        canvas_width = w
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas!= null){
            algs[currentAlg].drawing(this,canvas)
        }
    }

    fun ratioToSizeH(Ratio: Double):Float{
        var ratio:Double = Ratio
        if(ratio <0){
            ratio = 0.0
        }
        if(ratio > 1){
            ratio = 1.0
        }
        return (ratio * canvas_height.toDouble()).toFloat()
    }

    fun ratioToSizeW(Ratio: Double):Float{
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
        algs[currentAlg].nextState()
    }


}

abstract class AlgorithmHandler{
    
    abstract fun nextState()

    abstract fun drawing(ca : CanvasAnimation,canvas: Canvas)

}

class HamiltonPathAlgHandler: AlgorithmHandler() {

    private class Vertex(_id:Int,x_pos: Double, y_pos: Double) {
        var id:Int = _id
        var x:Double = x_pos
        var y:Double = y_pos
        var nei:MutableList<Vertex> = ArrayList()
        var last:Int = 0
        fun addNei(v : Vertex){
            nei.add(v)
        }
    }

    private class Edge(_v1:Vertex,_v2:Vertex){
        var v1:Vertex = _v1
        var v2:Vertex = _v2
    }

    private var vertexs:MutableList<Vertex> = ArrayList()
    private var edges:MutableList<Edge> = ArrayList()

    private var circle_painter:Paint = Paint()
    private var root_painter:Paint = Paint()
    private var line_painter:Paint = Paint()
    private var path_painter:Paint = Paint()
    private var verify_path_painter:Paint = Paint()

    private var history:MutableList<Vertex> = ArrayList()

    private var verifying_history:Int = 0

    private var isVisited:MutableList<Boolean> = arrayListOf(false,false,false,false,false)

    private var currentV:Vertex

    private var state = 0 // search = 0,retreat = 1,verify = 2

    init{
        // set painter
        circle_painter.strokeWidth = 1f
        circle_painter.color = Color.rgb(255,100,255)
        circle_painter.style = Paint.Style.FILL

        root_painter.strokeWidth = 1f
        root_painter.color = Color.GREEN
        root_painter.style = Paint.Style.FILL

        line_painter.strokeWidth = 15f
        line_painter.color = Color.BLACK
        line_painter.style = Paint.Style.STROKE

        path_painter.strokeWidth = 15f
        path_painter.color = Color.BLUE
        path_painter.style = Paint.Style.STROKE

        verify_path_painter.strokeWidth = 15f
        verify_path_painter.color = Color.RED
        verify_path_painter.style = Paint.Style.STROKE

        // build graphic
        vertexs.add(Vertex(0,0.25,0.5))
        vertexs.add(Vertex(1,0.5,0.33))
        vertexs.add(Vertex(2,0.75,0.33))
        vertexs.add(Vertex(3,0.5,0.66))
        vertexs.add(Vertex(4,0.75,0.66))



        edges.add(Edge(vertexs[0],vertexs[1]))
        vertexs[0].addNei(vertexs[1])
        vertexs[1].addNei(vertexs[0])
        edges.add(Edge(vertexs[1],vertexs[2]))
        vertexs[1].addNei(vertexs[2])
        vertexs[2].addNei(vertexs[1])
        edges.add(Edge(vertexs[1],vertexs[3]))
        vertexs[1].addNei(vertexs[3])
        vertexs[3].addNei(vertexs[1])
        edges.add(Edge(vertexs[0],vertexs[3]))
        vertexs[0].addNei(vertexs[3])
        vertexs[3].addNei(vertexs[0])
        edges.add(Edge(vertexs[1],vertexs[4]))
        vertexs[1].addNei(vertexs[4])
        vertexs[4].addNei(vertexs[1])
        edges.add(Edge(vertexs[2],vertexs[3]))
        vertexs[2].addNei(vertexs[3])
        vertexs[3].addNei(vertexs[2])
        edges.add(Edge(vertexs[3],vertexs[4]))
        vertexs[3].addNei(vertexs[4])
        vertexs[4].addNei(vertexs[3])
        edges.add(Edge(vertexs[2],vertexs[4]))
        vertexs[2].addNei(vertexs[4])
        vertexs[4].addNei(vertexs[2])


        isVisited[0] = true
        currentV = vertexs[0]
        history.add(vertexs[0])
    }

    private fun isAllVisited():Boolean{
        var bool = true
        for(b in isVisited){
            bool = bool && b
        }
        return bool
    }

    override fun nextState(){
        if(state == 2){
            verify_traversal()
        }
        if(state == 0){
            findingNext()
        }
        if(state == 1){
            retreatPrevious()
        }
        if(state == 3){
            resetGraph()
        }
    }

    private fun findingNext(){
        for(i in currentV.last..currentV.nei.size-1){
            currentV.last += 1
            if(!isVisited[currentV.nei[i].id]){
                isVisited[currentV.nei[i].id] = true
                history.add(currentV.nei[i])
                currentV = currentV.nei[i]
                return
            }
        }
        // if all nei vertex already visit
        if(isAllVisited() && (vertexs[0] in currentV.nei)){
            // if can go back to root
            history.add(vertexs[0])
            state = 2 // verifing
        }else{
            // path not valid, retreat
            if(currentV.last == currentV.nei.size){
                if(!(vertexs[0] in currentV.nei) or !isAllVisited()){
                    currentV.last = 0
                    isVisited[currentV.id] = false
                    state = 1
                }
            }
        }
    }

    private fun retreatPrevious(){
        history.removeLast()
        currentV = history.last()
        state = 0 // back to finding
    }

    private fun verify_traversal(){
        if(verifying_history < history.size - 1){
            verifying_history += 1
        }else{
            state = 3
        }
    }

    private fun resetGraph(){
        history.clear()
        verifying_history = 0
        state = 0
        for(vert in vertexs){
            vert.last = 0
        }
        isVisited.fill(false)
        isVisited[0] = true
        currentV = vertexs[0]
        history.add(vertexs[0])
    }

    override fun drawing(ca : CanvasAnimation,canvas: Canvas){
        for(edge in edges){
            canvas.drawLine(ca.ratioToSizeW(edge.v1.x),ca.ratioToSizeH(edge.v1.y),ca.ratioToSizeW(edge.v2.x),ca.ratioToSizeH(edge.v2.y),line_painter)
        }

        for( i in 0 until history.size - 1){
            canvas.drawLine(ca.ratioToSizeW(history[i].x),ca.ratioToSizeH(history[i].y),ca.ratioToSizeW(history[i+1].x),ca.ratioToSizeH(history[i+1].y),path_painter)
        }

        for( i in 0 until verifying_history){
            canvas.drawLine(ca.ratioToSizeW(history[i].x),ca.ratioToSizeH(history[i].y),ca.ratioToSizeW(history[i+1].x),ca.ratioToSizeH(history[i+1].y),verify_path_painter)
        }

        for(vert in vertexs){
            if(vert.id == 0){
                canvas.drawCircle(ca.ratioToSizeW(vert.x), ca.ratioToSizeH(vert.y), 50f, root_painter)
            }else{
                canvas.drawCircle(ca.ratioToSizeW(vert.x), ca.ratioToSizeH(vert.y), 50f, circle_painter)
            }
        }

    }
}

class HamiltonShortestPathHandler: AlgorithmHandler() {

    private class Vertex(_id:Int,x_pos: Double, y_pos: Double) {
        var id:Int = _id
        var x:Double = x_pos
        var y:Double = y_pos
        var nei:MutableList<Vertex> = ArrayList()
        var last:Int = 0
        fun addNei(v : Vertex){
            nei.add(v)
        }
    }

    private class Edge(_v1:Vertex,_v2:Vertex,w:Int){
        var v1:Vertex = _v1
        var v2:Vertex = _v2
        var weight:Int = w
    }

    private var vertexs:MutableList<Vertex> = ArrayList()
    private var edges:MutableList<Edge> = ArrayList()

    private var circle_painter:Paint = Paint()
    private var root_painter:Paint = Paint()
    private var line_painter:Paint = Paint()
    private var path_painter:Paint = Paint()
    private var verify_path_painter:Paint = Paint()
    private var text_painter:Paint = TextPaint()
    private var weight_text_painter:Paint = TextPaint()

    private var history:MutableList<Vertex> = ArrayList()

    private var verifying_history:Int = 0
    private var minium_history:MutableList<Vertex> = ArrayList()

    private var isVisited:MutableList<Boolean> = arrayListOf(false,false,false,false,false)
    private var currentV:Vertex
    private var state = 0
    private var currentWeight = 0
    private var minWeight = 1000
    private var isShowingResult = false

    init{
        // set painter
        circle_painter.strokeWidth = 1f
        circle_painter.color = Color.rgb(255,100,255)
        circle_painter.style = Paint.Style.FILL

        root_painter.strokeWidth = 1f
        root_painter.color = Color.GREEN
        root_painter.style = Paint.Style.FILL

        line_painter.strokeWidth = 15f
        line_painter.color = Color.BLACK
        line_painter.style = Paint.Style.STROKE

        path_painter.strokeWidth = 15f
        path_painter.color = Color.BLUE
        path_painter.style = Paint.Style.STROKE

        verify_path_painter.strokeWidth = 15f
        verify_path_painter.color = Color.RED
        verify_path_painter.style = Paint.Style.STROKE

        text_painter.color = Color.BLACK
        text_painter.textSize = (64).toFloat()
        text_painter.isAntiAlias = true
        text_painter.style = Paint.Style.STROKE
        text_painter.strokeWidth = 10f

        weight_text_painter.color = Color.BLACK
        weight_text_painter.textSize = (64).toFloat()
        weight_text_painter.isAntiAlias = true

        // build graphic
        vertexs.add(Vertex(0,0.25,0.5))
        vertexs.add(Vertex(1,0.5,0.33))
        vertexs.add(Vertex(2,0.75,0.33))
        vertexs.add(Vertex(3,0.5,0.66))
        vertexs.add(Vertex(4,0.75,0.66))

        edges.add(Edge(vertexs[0],vertexs[1],5))
        vertexs[0].addNei(vertexs[1])
        vertexs[1].addNei(vertexs[0])
        edges.add(Edge(vertexs[1],vertexs[2],3))
        vertexs[1].addNei(vertexs[2])
        vertexs[2].addNei(vertexs[1])
        edges.add(Edge(vertexs[1],vertexs[3],4))
        vertexs[1].addNei(vertexs[3])
        vertexs[3].addNei(vertexs[1])
        edges.add(Edge(vertexs[0],vertexs[3],2))
        vertexs[0].addNei(vertexs[3])
        vertexs[3].addNei(vertexs[0])
        edges.add(Edge(vertexs[1],vertexs[4],7))
        vertexs[1].addNei(vertexs[4])
        vertexs[4].addNei(vertexs[1])
        edges.add(Edge(vertexs[2],vertexs[3],5))
        vertexs[2].addNei(vertexs[3])
        vertexs[3].addNei(vertexs[2])
        edges.add(Edge(vertexs[3],vertexs[4],4))
        vertexs[3].addNei(vertexs[4])
        vertexs[4].addNei(vertexs[3])
        edges.add(Edge(vertexs[2],vertexs[4],1))
        vertexs[2].addNei(vertexs[4])
        vertexs[4].addNei(vertexs[2])


        isVisited[0] = true
        currentV = vertexs[0]
        history.add(vertexs[0])
    }

    private fun isAllVisited():Boolean{
        var bool = true
        for(b in isVisited){
            bool = bool && b
        }
        return bool
    }

    override fun nextState(){
        if(state == 8){
            resetGraph()
        }
        if(state > 1 && state < 8){
            showResult()
        }
        if(state == 1){
            retreatPrevious()
        }
        if(state == 0){
            findingNext()
        }
    }

    private fun findEdgeWeight(v1:Vertex,v2:Vertex):Int{
        for(e in edges){
            if((e.v1 == v1 && e.v2 == v2) || (e.v2 == v1 && e.v1 == v2)){
                return e.weight
            }
        }
        return -1
    }

    private fun findingNext(){
        for(i in currentV.last..currentV.nei.size-1){
            currentV.last += 1
            if(!isVisited[currentV.nei[i].id]){
                isVisited[currentV.nei[i].id] = true
                history.add(currentV.nei[i])
                currentWeight += findEdgeWeight(currentV,currentV.nei[i])
                currentV = currentV.nei[i]
                return
            }
        }
        // if all nei vertex already visit
        if(isAllVisited() && (vertexs[0] in currentV.nei)){
            // if can go back to root
            history.add(vertexs[0])
            currentWeight += findEdgeWeight(currentV,vertexs[0])
            verifying_history = history.size - 1
            if(minWeight > currentWeight){
                minium_history = history.toMutableList()
                minWeight = currentWeight
            }
            minWeight = min(minWeight,currentWeight)
        }
        currentV.last = 0
        isVisited[currentV.id] = false
        state = 1

    }

    private fun retreatPrevious(){
        verifying_history = 0
        if(history.size > 1){
            currentWeight -= findEdgeWeight(history.last(),history[history.lastIndex - 1])
            history.removeLast()
            currentV = history.last()
            state = 0 // back to finding
        }else{
            state = 2
        }

    }

    private fun resetGraph(){
        history.clear()
        verifying_history = 0
        state = 0
        for(vert in vertexs){
            vert.last = 0
        }
        isVisited.fill(false)
        isVisited[0] = true
        currentV = vertexs[0]
        history.add(vertexs[0])
        currentWeight = 0
        minWeight = 10000
        isShowingResult = false
        minium_history.clear()
    }

    private fun showResult(){
        isShowingResult = true
        state += 1
    }

    override fun drawing(ca : CanvasAnimation,canvas: Canvas){

        fun edgeToTextCoordinate(e : Edge):Pair<Float,Float>{

            val x:Float = ca.ratioToSizeW((e.v1.x + e.v2.x) / 2)
            val y:Float = ca.ratioToSizeH((e.v1.y + e.v2.y)/ 2)

            return Pair(x,y)
        }

        for(edge in edges){
            canvas.drawLine(ca.ratioToSizeW(edge.v1.x),ca.ratioToSizeH(edge.v1.y),ca.ratioToSizeW(edge.v2.x),ca.ratioToSizeH(edge.v2.y),line_painter)
        }

        for( i in 0 until history.size - 1){
            canvas.drawLine(ca.ratioToSizeW(history[i].x),ca.ratioToSizeH(history[i].y),ca.ratioToSizeW(history[i+1].x),ca.ratioToSizeH(history[i+1].y),path_painter)
        }

        for( i in 0 until verifying_history){
            canvas.drawLine(ca.ratioToSizeW(history[i].x),ca.ratioToSizeH(history[i].y),ca.ratioToSizeW(history[i+1].x),ca.ratioToSizeH(history[i+1].y),verify_path_painter)
        }

        if(isShowingResult){
            for( i in 0 until minium_history.size - 1){
                canvas.drawLine(ca.ratioToSizeW(minium_history[i].x),ca.ratioToSizeH(minium_history[i].y),ca.ratioToSizeW(minium_history[i+1].x),ca.ratioToSizeH(minium_history[i+1].y),verify_path_painter)
            }
        }

        for(vert in vertexs){
            if(vert.id == 0){
                canvas.drawCircle(ca.ratioToSizeW(vert.x), ca.ratioToSizeH(vert.y), 50f, root_painter)
            }else{
                canvas.drawCircle(ca.ratioToSizeW(vert.x), ca.ratioToSizeH(vert.y), 50f, circle_painter)
            }
        }

        var x:Float
        var y:Float
        var x_y:Pair<Float,Float>

        x_y = edgeToTextCoordinate(edges[0])
        x = x_y.first
        y = x_y.second
        canvas.drawText(edges[0].weight.toString(), x - 30f, y - 30f, text_painter)

        x_y = edgeToTextCoordinate(edges[1])
        x = x_y.first
        y = x_y.second
        canvas.drawText(edges[1].weight.toString(), x - 20f, y - 30f, text_painter)

        x_y = edgeToTextCoordinate(edges[2])
        x = x_y.first
        y = x_y.second
        canvas.drawText(edges[2].weight.toString(), x - 50f, y, text_painter)

        x_y = edgeToTextCoordinate(edges[3])
        x = x_y.first
        y = x_y.second
        canvas.drawText(edges[3].weight.toString(), x, y - 20f, text_painter)

        x_y = edgeToTextCoordinate(edges[4])
        x = x_y.first
        y = x_y.second
        canvas.drawText(edges[4].weight.toString(), x - 50f, y - 80f, text_painter)

        x_y = edgeToTextCoordinate(edges[5])
        x = x_y.first
        y = x_y.second
        canvas.drawText(edges[5].weight.toString(), x - 50f, y + 120f,text_painter)

        x_y = edgeToTextCoordinate(edges[6])
        x = x_y.first
        y = x_y.second
        canvas.drawText(edges[6].weight.toString(), x - 20f, y + 60f,text_painter)

        x_y = edgeToTextCoordinate(edges[7])
        x = x_y.first
        y = x_y.second
        canvas.drawText(edges[7].weight.toString(), x + 20f, y + 20f,text_painter)
        if(currentWeight != 0){
            canvas.drawText("current : " + currentWeight.toString(),ca.ratioToSizeW(0.5),ca.ratioToSizeH(0.8),weight_text_painter)
        }else{
            canvas.drawText("current : NaN",ca.ratioToSizeW(0.5),ca.ratioToSizeH(0.8),weight_text_painter)
        }
        if(minWeight < 1000){
            canvas.drawText("Mininum : " + minWeight.toString(),ca.ratioToSizeW(0.5),ca.ratioToSizeH(0.9),weight_text_painter)
        }else{
            canvas.drawText("Mininum : NaN",ca.ratioToSizeW(0.5),ca.ratioToSizeH(0.9),weight_text_painter)
        }
    }
}