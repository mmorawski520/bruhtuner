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

class MainActivity : CanvasDrawer() {

    var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    var permissionGranted: Boolean = false
    var isTuning: Boolean = false
    var isAutoTuning: Boolean = true


    var gCurrentString: GuitarStrings = GuitarStrings.none


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


        val stringRadioBoxes = arrayOfNulls<RadioButton>(6)
        stringRadioBoxes[0] = findViewById(R.id.radioButtonFirstString)
        stringRadioBoxes[1] = findViewById(R.id.radioButtonSecondString)
        stringRadioBoxes[2] = findViewById(R.id.radioButtonThirdString)
        stringRadioBoxes[3] = findViewById(R.id.radioButtonFourthString)
        stringRadioBoxes[4] = findViewById(R.id.radioButtonFifthString)
        stringRadioBoxes[5] = findViewById(R.id.radioButtonSixthString)

        initializeColors()
        initializeFrequencyCanvasLine()
        initializeFrequencyPointerCanvas()
        refreshStringCanvas()

        binding.imageView.background = BitmapDrawable(getResources(), bitmap)
        binding.imageViewFrequencyPointer.background = BitmapDrawable(getResources(), pointerBitmap)

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
                gCurrentString = drawSelectedStringManually(i)
            })
        }
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

    private fun startRecording() {
        isTuning = true
        signal.startTuning()
        tune();
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        binding.frequencyTextView.text = "bruh"
        startRecording()
    }

    private fun tune() {
        handler.post(object : Runnable {
            override fun run() {
                var calculated = false
                while (!calculated) {
                    if (signal.isHumming()) { //signal is loud enough
                        calculated = true
                        val sig: ShortArray =
                            signal.getSignal() //get samples of a signal to transform
                        val fourierData = signal.calculateDFT(sig)
                        val fourier = fourierData[0] as DoubleArray //transformed signal
                        val maxFreq = fourierData[1] as Int
                        val peaks: IntArray = signal.detectFrequencyPeak(fourier)
                        var string: GuitarStrings = gCurrentString

                        if (isAutoTuning == true) {
                            string = signal.detectPlayedString(maxFreq, peaks)
                        }

                        val detuning: Int = signal.tuneGuitar(
                            string,
                            maxFreq,
                            peaks
                        )

                        //the difference between ideal and calculated value (in Hz)
                        binding.frequencyTextView.text = detuning.toString() + string.toString()
                        drawStringSelectionAuto(string.toString())

                        frequencyPointerCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                        frequencyPointerCanvas.drawPoint(500f + detuning, 300f, cyanPaint)

                        showTip(detuning)

                        if (detuning == 0 && string != GuitarStrings.none)
                            binding.frequencyTextView.setBackgroundColor(
                                Color.argb(
                                    33,
                                    127,
                                    255,
                                    0
                                )
                            )
                        else
                            binding.frequencyTextView.setBackgroundColor(
                                Color.argb(
                                    33,
                                    220,
                                    20,
                                    60
                                )
                            )
                    }
                }
                if (isTuning) handler.postDelayed(this, 50) else handler.removeCallbacks(this)
            }
        })
    }

    fun showTip(detuning: Int) {
        if (detuning < 0)
            binding.textViewTuningHelper.text = "Too High"
        if (detuning > 0)
            binding.textViewTuningHelper.text = "Too Low"
        if (detuning == 0)
            binding.textViewTuningHelper.text = "Perfect"
    }
}