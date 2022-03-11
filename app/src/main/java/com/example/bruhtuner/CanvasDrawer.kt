package com.example.bruhtuner

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity

open class CanvasDrawer : AppCompatActivity() {
    lateinit var bitmap: Bitmap
    lateinit var pointerBitmap: Bitmap
    lateinit var whitePaint: Paint
    lateinit var redPaint: Paint
    lateinit var cyanPaint: Paint
    lateinit var frequencyCanvasLine: Canvas
    lateinit var frequencyPointerCanvas: Canvas
    var width: Float = 1000f


    fun selectSmallEString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2) + 700, 410f, (width / 2) + 825, redPaint)
        frequencyCanvasLine.drawLine(410f, (width / 2) + 830, 410f, (width / 2) + 2500, redPaint)
    }

    fun selectBString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2) + 400, 610f, (width / 2) + 730, redPaint)
        frequencyCanvasLine.drawLine(610f, (width / 2) + 730, 610f, (width / 2) + 2500, redPaint)
    }

    fun selectGString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2) + 100, 810f, (width / 2) + 630, redPaint)
        frequencyCanvasLine.drawLine(810f, (width / 2) + 630, 810f, (width / 2) + 2500, redPaint)
    }

    fun selectDString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 100, 1190f, (width / 2) + 630, redPaint)
        frequencyCanvasLine.drawLine(1190f, (width / 2) + 630, 1190f, (width / 2) + 2500, redPaint)
    }

    fun selectAString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 400, 1390f, (width / 2) + 730, redPaint)
        frequencyCanvasLine.drawLine(1390f, (width / 2) + 730, 1390f, (width / 2) + 2500, redPaint)
    }

    fun selectEString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 700, 1590f, (width / 2) + 825, redPaint)
        frequencyCanvasLine.drawLine(1590f, (width / 2) + 830, 1590f, (width / 2) + 2500, redPaint)
    }

    fun initializeFrequencyCanvasLine() {
        bitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888)

        frequencyCanvasLine = Canvas(bitmap)
        frequencyCanvasLine.drawARGB(255, 41, 45, 44);
        frequencyCanvasLine.drawLine(width, (width / 2), width, (width / 2) - 600, whitePaint)
    }

    fun initializeFrequencyPointerCanvas() {
        pointerBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        frequencyPointerCanvas = Canvas(pointerBitmap)
        frequencyPointerCanvas.drawARGB(0, 41, 45, 44);
    }

    fun refreshStringCanvas() {
        whitePaint.setStrokeWidth(35f)
        whitePaint.setStrokeCap(Paint.Cap.ROUND)

        //smallE
        frequencyCanvasLine.drawLine(300f, (width / 2) + 700, 410f, (width / 2) + 825, whitePaint)
        frequencyCanvasLine.drawLine(410f, (width / 2) + 830, 410f, (width / 2) + 2500, whitePaint)

        //B
        frequencyCanvasLine.drawLine(300f, (width / 2) + 400, 610f, (width / 2) + 730, whitePaint)
        frequencyCanvasLine.drawLine(610f, (width / 2) + 730, 610f, (width / 2) + 2500, whitePaint)

        //G
        frequencyCanvasLine.drawLine(300f, (width / 2) + 100, 810f, (width / 2) + 630, whitePaint)
        frequencyCanvasLine.drawLine(810f, (width / 2) + 630, 810f, (width / 2) + 2500, whitePaint)

        //D
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 100, 1190f, (width / 2) + 630, whitePaint)
        frequencyCanvasLine.drawLine(
            1190f,
            (width / 2) + 630,
            1190f,
            (width / 2) + 2500,
            whitePaint
        )

        //A
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 400, 1390f, (width / 2) + 730, whitePaint)
        frequencyCanvasLine.drawLine(
            1390f,
            (width / 2) + 730,
            1390f,
            (width / 2) + 2500,
            whitePaint
        )

        //E
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 700, 1590f, (width / 2) + 825, whitePaint)
        frequencyCanvasLine.drawLine(
            1590f,
            (width / 2) + 830,
            1590f,
            (width / 2) + 2500,
            whitePaint
        )

    }

    fun initializeColors() {
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

        cyanPaint = Paint()
        cyanPaint.setColor(Color.parseColor("#5cb9f7"))
        cyanPaint.setStrokeWidth(15f)
        cyanPaint.setStyle(Paint.Style.STROKE)
        cyanPaint.setAntiAlias(true)
        cyanPaint.setDither(true)
        cyanPaint.setStrokeCap(Paint.Cap.ROUND)
        cyanPaint.setStrokeJoin(Paint.Join.ROUND)
    }

    fun drawStringSelectionAuto(cString: String) {
        if (cString == "secondE")
            selectSmallEString()
        if (cString == "B")
            selectBString()
        if (cString == "G")
            selectGString()
        if (cString == "D")
            selectDString()
        if (cString == "A")
            selectAString()
        if (cString == "E")
            selectEString()
        if (cString == "none")
            refreshStringCanvas()
    }

    fun drawSelectedStringManually(selectedString: Int): GuitarStrings {
        if (selectedString == 2) {
            selectSmallEString()
            return GuitarStrings.secondE
        }
        if (selectedString == 1) {
            selectBString()
            return GuitarStrings.B
        }
        if (selectedString == 0) {
            selectGString()
            return GuitarStrings.G
        }
        if (selectedString == 3) {
            selectDString()
            return GuitarStrings.D
        }
        if (selectedString == 4) {
            selectAString()
            return GuitarStrings.A
        }
        if (selectedString == 5) {
            selectEString()
            return GuitarStrings.E
        }
        return GuitarStrings.none
    }
}