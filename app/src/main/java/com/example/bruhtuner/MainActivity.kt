package com.example.bruhtuner


import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.bruhtuner.databinding.ActivityMainBinding

const val REQUEST_CODE = 200

class MainActivity : AppCompatActivity() {

    var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    var permissionGranted: Boolean = false
    var isTuning: Boolean = false
    var isAutoTuning: Boolean = false
    var selectedString: Int = 0
    var width: Float = 0f
    var gCurrentString: String = ""

    lateinit var bitmap: Bitmap
    lateinit var whitePaint: Paint
    lateinit var redPaint: Paint
    lateinit var cyanPaint: Paint

    lateinit var frequencyCanvasLine: Canvas
    lateinit var signal: MicSignal
    lateinit var handler: Handler
    lateinit var binding: ActivityMainBinding
    val runnable = object : Runnable {
        override fun run() {

            handler.postDelayed(this, 50)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        permissionGranted = ActivityCompat.checkSelfPermission(
            this,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted)
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)

        signal = MicSignal()
        handler = Handler()


        //I'll add it later I

        val displayMetrics = DisplayMetrics()

        val stringRadioBoxes = arrayOfNulls<RadioButton>(6)
        stringRadioBoxes[0] = findViewById(R.id.radioButtonFirstString)
        stringRadioBoxes[1] = findViewById(R.id.radioButtonSecondString)
        stringRadioBoxes[2] = findViewById(R.id.radioButtonThirdString)
        stringRadioBoxes[3] = findViewById(R.id.radioButtonFourthString)
        stringRadioBoxes[4] = findViewById(R.id.radioButtonFifthString)
        stringRadioBoxes[5] = findViewById(R.id.radioButtonSixthString)


        windowManager.defaultDisplay.getMetrics(displayMetrics)

        width = 1000f
        bitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888)

        initializeColors()
        initializeFrequencyCanvasLine()
        refreshStringCanvas()

        binding.imageView.background = BitmapDrawable(getResources(), bitmap)

        binding.expandTuningDialogBtn.setOnClickListener {
            val intent = Intent(this, TuningActivity::class.java)
            startActivity(intent)
        }
        binding.switch2.setOnClickListener {
            isAutoTuning = binding.switch2.isChecked()
            if (isAutoTuning == true)
                Toast.makeText(this, "Autotuning is ON", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Autotuning is OFF", Toast.LENGTH_SHORT).show()
            for (i in 0..5) {
                stringRadioBoxes[i]?.isEnabled = !stringRadioBoxes[i]!!.isEnabled()
                if (stringRadioBoxes[i]?.isEnabled == true)
                    stringRadioBoxes[i]?.visibility = View.VISIBLE
                else
                    stringRadioBoxes[i]?.visibility = View.INVISIBLE
            }
        }
        for (i in 0..5) {
            stringRadioBoxes[i]?.setOnClickListener(View.OnClickListener {
                for (j in 0..5) {
                    stringRadioBoxes[j]?.isChecked = false

                }
                stringRadioBoxes[i]!!.isChecked = true
                selectedString = i
                drawSelectedString()
            })
        }

    }
    private fun drawSelectedString(){
        if(selectedString==0){
            selectSmallEString()
            gCurrentString="secondE"
        }

        if(selectedString==1) {
            selectBString()
            gCurrentString="B"
        }

        if(selectedString==2){
            selectGString()
            gCurrentString="G"
        }
        if(selectedString==3){
            selectDString()
            gCurrentString="D"
        }
        if(selectedString==4) {
            selectAString()
            gCurrentString="A"
        }
        if(selectedString==5) {
            selectEString()
            gCurrentString="E"
        }
    }
    private fun initializeColors() {
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
        cyanPaint.setColor(Color.parseColor("#cd00cd"))
        cyanPaint.setStrokeWidth(25f)
        cyanPaint.setStyle(Paint.Style.STROKE)
        cyanPaint.setAntiAlias(true)
        cyanPaint.setDither(true)
        cyanPaint.setStrokeCap(Paint.Cap.ROUND)
        cyanPaint.setStrokeJoin(Paint.Join.ROUND)

    }

    private fun initializeFrequencyCanvasLine() {
        frequencyCanvasLine = Canvas(bitmap)
        frequencyCanvasLine.drawARGB(255, 41, 45, 44);
        frequencyCanvasLine.drawLine(width, (width / 2), width, (width / 2) - 600, whitePaint)


    }

    private fun refreshStringCanvas() {
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

    private fun selectSmallEString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2) + 100, 810f, (width / 2) + 630, redPaint)
        frequencyCanvasLine.drawLine(810f, (width / 2) + 630, 810f, (width / 2) + 2500, redPaint)
    }

    private fun selectBString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2) + 400, 610f, (width / 2) + 730, redPaint)
        frequencyCanvasLine.drawLine(610f, (width / 2) + 730, 610f, (width / 2) + 2500, redPaint)
    }

    private fun selectGString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(300f, (width / 2) + 700, 410f, (width / 2) + 825, redPaint)
        frequencyCanvasLine.drawLine(410f, (width / 2) + 830, 410f, (width / 2) + 2500, redPaint)
    }

    private fun selectDString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 100, 1190f, (width / 2) + 630, redPaint)
        frequencyCanvasLine.drawLine(1190f, (width / 2) + 630, 1190f, (width / 2) + 2500, redPaint)
    }

    private fun selectAString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 400, 1390f, (width / 2) + 730, redPaint)
        frequencyCanvasLine.drawLine(1390f, (width / 2) + 730, 1390f, (width / 2) + 2500, redPaint)
    }

    private fun selectEString() {
        refreshStringCanvas()
        frequencyCanvasLine.drawLine(1700f, (width / 2) + 700, 1590f, (width / 2) + 825, redPaint)
        frequencyCanvasLine.drawLine(1590f, (width / 2) + 830, 1590f, (width / 2) + 2500, redPaint)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE)
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
    }
    private fun startRecording(){
        isTuning=true
        signal.startTuning()
        tune();
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

        binding.frequencyTextView.text="bruh"
     //  startRecording()
    }
    private fun drawStringSelection(cString:String){
     //   if(secondE, B, G, D, A, E, none)
        if(cString=="secondE")
            selectSmallEString()
        if(cString=="B")
            selectBString()
        if(cString=="G")
            selectGString()
        if(cString=="D")
            selectDString()
        if(cString=="A")
            selectAString()
        if(cString=="E")
            selectEString()
        if(cString=="none")
            refreshStringCanvas()
    }
    private fun tune(){
        handler.post(object : Runnable {
            override fun run() {
                var calculated = false
                while (!calculated) {
                    if (signal.isHumming()) { //signal is loud enough
                        calculated = true
                        val sig: ShortArray = signal.getSignal() //get samples of a signal to transform
                        val fourierData = signal.calculateDFT(sig)
                        val fourier = fourierData[0] as DoubleArray //transformed signal
                        val maxFreq = fourierData[1] as Int
                        val peaks: IntArray = signal.detectFrequencyPeak(fourier)
                      //  if(isAutoTuning==true)
                            val string: GuitarStrings = signal.detectPlayedString(maxFreq, peaks)


                        val detuning: Int = signal.tuneGuitar(
                            string,
                            maxFreq,
                            peaks
                        ) //the difference between ideal and calculated value (in Hz)

                        binding.frequencyTextView.text=detuning.toString()+ string.toString()
                        drawStringSelection(string.toString())
                        frequencyCanvasLine.drawPoint(detuning.toFloat(),150f,cyanPaint)
                    }
                }
                if (isTuning) handler.postDelayed(this, 50) else handler.removeCallbacks(this)
            }
        })
    }

}