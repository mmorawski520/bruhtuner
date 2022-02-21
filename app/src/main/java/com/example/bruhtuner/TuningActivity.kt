package com.example.bruhtuner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner

class TuningActivity : AppCompatActivity() {
    val instrumentArray = arrayOf("Guitar","Ukulele","Banjo","Bass")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuning)

        val instrumentSpinner = findViewById<Spinner>(R.id.instrumentSpinner)
        val instrumentArrayAdapter = ArrayAdapter(this,R.layout.custom_spinner_item,instrumentArray)
        val collapseTuningDialogBtn = findViewById<ImageButton>(R.id.collapseTuningDialogBtn)

        instrumentSpinner.adapter = instrumentArrayAdapter

        collapseTuningDialogBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}