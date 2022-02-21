package com.example.bruhtuner


import android.app.Dialog
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    var width: Float = 0f;
    lateinit var bitmap: Bitmap
    lateinit var whitePaint: Paint
    lateinit var redPaint: Paint
    lateinit var frequencyCanvasLine: Canvas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val expandTuningDialogBtn = findViewById<ImageButton>(R.id.expandTuningDialogBtn)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        width = 1000f
        bitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888)

        initializeColors()
        initializeFrequencyCanvasLine()
        refreshStringCanvas()
        selectEString()
        imageView.background = BitmapDrawable(getResources(), bitmap)

     expandTuningDialogBtn.setOnClickListener{
         val intent = Intent(this, TuningActivity::class.java)
         startActivity(intent)
        }

    }
    private fun initializeColors(){
        whitePaint = Paint()
        whitePaint.setColor(Color.parseColor("#fcfcfc"))
        whitePaint.setStrokeWidth(25f)
        whitePaint.setStyle(Paint.Style.STROKE)
        whitePaint.setAntiAlias(true)
        whitePaint.setDither(true)
        whitePaint.setStrokeJoin(Paint.Join.ROUND)

        redPaint = Paint()
        redPaint.setColor(Color.parseColor("#b00b69"))
        redPaint.setStrokeWidth(35f)
        redPaint.setStyle(Paint.Style.STROKE)
        redPaint.setAntiAlias(true)
        redPaint.setDither(true)
        redPaint.setStrokeCap(Paint.Cap.ROUND)
        redPaint.setStrokeJoin(Paint.Join.ROUND)
        
    }
    private fun initializeFrequencyCanvasLine(){
        frequencyCanvasLine = Canvas(bitmap)
        frequencyCanvasLine.drawARGB(255, 41, 45, 44);

        frequencyCanvasLine.drawLine(width, (width / 2), width, (width / 2) - 600, whitePaint)
    }
    private fun refreshStringCanvas(){
        whitePaint.setStrokeWidth(35f)
        whitePaint.setStrokeCap(Paint.Cap.ROUND)


        //smallE
        frequencyCanvasLine.drawLine(300f, (width / 2)+700, 410f, (width / 2) + 825, whitePaint)
        frequencyCanvasLine.drawLine(410f, (width / 2) + 830, 410f, (width / 2) + 2500, whitePaint)

        //B
        frequencyCanvasLine.drawLine(300f, (width / 2)+400, 610f, (width / 2) + 730, whitePaint)
        frequencyCanvasLine.drawLine(610f, (width / 2) + 730, 610f, (width / 2) + 2500, whitePaint)

        //G
        frequencyCanvasLine.drawLine(300f, (width / 2)+100, 810f, (width / 2) + 630, whitePaint)
        frequencyCanvasLine.drawLine(810f, (width / 2) + 630, 810f, (width / 2) + 2500, whitePaint)

        //D
        frequencyCanvasLine.drawLine(1700f, (width / 2)+100, 1190f, (width / 2) + 630, whitePaint)
        frequencyCanvasLine.drawLine(1190f, (width / 2) + 630, 1190f, (width / 2) + 2500, whitePaint)

        //A
        frequencyCanvasLine.drawLine(1700f, (width / 2)+400, 1390f, (width / 2) + 730, whitePaint)
        frequencyCanvasLine.drawLine(1390f, (width / 2) + 730, 1390f, (width / 2) + 2500, whitePaint)

        //E
        frequencyCanvasLine.drawLine(1700f, (width / 2)+700, 1590f, (width / 2) + 825, whitePaint)
        frequencyCanvasLine.drawLine(1590f, (width / 2) + 830, 1590f, (width / 2) + 2500, whitePaint)

    }
    private fun selectSmallEString(){
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2)+700, 410f, (width / 2) + 825, redPaint)
        frequencyCanvasLine.drawLine(410f, (width / 2) + 830, 410f, (width / 2) + 2500, redPaint)
    }
    private fun selectBString(){
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2)+400, 610f, (width / 2) + 730, redPaint)
        frequencyCanvasLine.drawLine(610f, (width / 2) + 730, 610f, (width / 2) + 2500, redPaint)
    }
    private fun selectGString(){
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2)+100, 810f, (width / 2) + 630, redPaint)
        frequencyCanvasLine.drawLine(810f, (width / 2) + 630, 810f, (width / 2) + 2500, redPaint)
    }
    private fun selectDString(){
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2)+100, 1190f, (width / 2) + 630, redPaint)
        frequencyCanvasLine.drawLine(1190f, (width / 2) + 630, 1190f, (width / 2) + 2500, redPaint)
    }
    private fun selectAString(){
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2)+400, 1390f, (width / 2) + 730, redPaint)
        frequencyCanvasLine.drawLine(1390f, (width / 2) + 730, 1390f, (width / 2) + 2500, redPaint)
    }
    private fun selectEString(){
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2)+700, 1590f, (width / 2) + 825, redPaint)
        frequencyCanvasLine.drawLine(1590f, (width / 2) + 830, 1590f, (width / 2) + 2500, redPaint)
    }
}