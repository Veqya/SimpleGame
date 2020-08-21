package com.example.simplegame

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
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
    var velocity: Int = 0;
    var gravity: Int = 3;
    var gameState: Boolean = false
    var gap: Int = 400
    var minTubeOfSet: Int = 500
    var maxTubeOfSet: Int = 500
    var numberOfTubes: Int = 3
    var distanceBetweenTubes: Int
    var tubeX = IntArray(numberOfTubes)
    var tubeY = IntArray(numberOfTubes)
    var topTube: Bitmap
    var bottomTube: Bitmap
    var tubeVelocity: Int = 8
    var flapSound: MediaPlayer? = MediaPlayer.create(context, R.raw.flap)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, null, rect, null)
        birdFrame = if (birdFrame == 0) {
            1
        } else {
            0
        }

        var birdMaxBottom: Int = dHeight - birds[0]!!.height

        if (gameState) {
            if (birdY < birdMaxBottom || velocity < 0) {
                velocity += gravity
                birdY += velocity
            }
            for (i in 0..numberOfTubes - 1) {
                tubeX[i] -= tubeVelocity
                if (tubeX[i] < topTube.width - 500) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes
                }
                canvas.drawBitmap(
                    topTube, tubeX[i].toFloat(), (tubeY[i] - topTube.height).toFloat(), null
                )

                canvas.drawBitmap(
                    bottomTube, tubeX[i].toFloat(), (tubeY[i] + gap).toFloat(), null
                )
                if (birdY <= tubeY[i]  || birdY >= tubeY[i] + gap- birds[0]?.height!!) {
                    if (birdX>tubeX[i]-bottomTube.width/2&&birdX<tubeX[i]+bottomTube.width/2){
                        playFlap()
                    }
                }





            }
        }
        canvas.drawBitmap(birds[birdFrame]!!, birdX.toFloat(), birdY.toFloat(), null)
        handler.postDelayed(runnable, UPDATE_MILIS.toLong())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        playFlap()

        val action = event?.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                velocity = -30
                gameState = true
            }
        }
        return true
    }

    private fun playFlap() {
        flapSound?.start()
    }

    private fun pauseFlap() {
        flapSound?.pause()
    }

    init {
        val display: Display
        val handler: Handler
        runnable = Runnable { invalidate() }
        background = BitmapFactory.decodeResource(resources, R.drawable.background)
        topTube = BitmapFactory.decodeResource(resources, R.drawable.toptube)
        bottomTube = BitmapFactory.decodeResource(resources, R.drawable.bottomtube)
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
        distanceBetweenTubes = dWidth * 3 / 5
        minTubeOfSet = gap / 2
        maxTubeOfSet = dHeight - minTubeOfSet - gap


        var random: java.util.Random = java.util.Random()
        for (i in 0..numberOfTubes - 1) {
            tubeX[i] = i * distanceBetweenTubes
            tubeY[i] = minTubeOfSet + random.nextInt(maxTubeOfSet - minTubeOfSet + 1)
        }


    }
}


