package com.example.simplegame

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.view.Display
import android.view.MotionEvent
import android.view.View

class GameView(context: Context?) : View(context) {


    var runnable: Runnable
    val UPDATE_MILIS = 30
    var background: Bitmap
    var point: Point
    var dWidth: Int
    var dHeight: Int
    var rect: Rect
    var birds: Array<Bitmap?>
    var birdFrame = 0
    var birdX: Int
    var birdY: Int
    var velocity:Int = 0;
    var gravity:Int = 3;

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, null, rect, null)
        birdFrame = if (birdFrame == 0) {
            1
        } else {
            0
        }

        var birdMaxBottom:Int = dHeight- birds[0]!!.height
        if (birdY<birdMaxBottom || velocity<0) {
            velocity+=gravity
            birdY+=velocity
        }


        canvas.drawBitmap(birds[birdFrame]!!, birdX.toFloat(), birdY.toFloat(), null)
        handler.postDelayed(runnable, UPDATE_MILIS.toLong())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action =event?.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                velocity= -30
            }
        }

        return true
    }

    init {
        val display: Display
        val handler: Handler
        handler = Handler()
        runnable = Runnable { invalidate() }
        background = BitmapFactory.decodeResource(resources, R.drawable.background)
        display = (getContext() as Activity).windowManager.defaultDisplay
        point = Point()
        display.getSize(point)
        dWidth = point.x
        dHeight = point.y
        rect = Rect(0, 0, dWidth, dHeight)
        birds = arrayOfNulls(2)
        birds[0] = BitmapFactory.decodeResource(resources, R.drawable.bird1)
        birds[1] = BitmapFactory.decodeResource(resources, R.drawable.bird2)
        birdX = dWidth / 2 - birds[0]!!.getWidth() / 2
        birdY = dHeight / 2 - birds[0]!!.getHeight() / 2
    }
}