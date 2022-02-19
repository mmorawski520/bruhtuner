package com.example.bruhtuner


import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    var width: Float = 0f;
    lateinit var bitmap: Bitmap
    lateinit var whitePaint: Paint
    lateinit var frequencyCanvasLine: Canvas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        width = displayMetrics.widthPixels.toFloat()
        bitmap = Bitmap.createBitmap((width * 2).toInt(), width.toInt(), Bitmap.Config.ARGB_8888)

        initializeColors()
        initializeCanvasLine()

        imageView.background = BitmapDrawable(getResources(), bitmap)
    }
    private fun initializeColors(){
        whitePaint = Paint()
        whitePaint.setColor(Color.parseColor("#fcfcfc"))
        whitePaint.setStrokeWidth(25f)
        whitePaint.setStyle(Paint.Style.STROKE)
        whitePaint.setAntiAlias(true)
        whitePaint.setDither(true)
    }
    private fun initializeCanvasLine(){
        frequencyCanvasLine = Canvas(bitmap)
        frequencyCanvasLine.drawARGB(255, 41, 45, 44);
        frequencyCanvasLine.drawPoint(width, (width / 2), whitePaint)
        frequencyCanvasLine.drawLine(width, (width / 2), width, (width / 2) - 600, whitePaint)
    }
}