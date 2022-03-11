package com.example.bruhtuner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TuningActivity : AppCompatActivity() {
    val instrumentArray = arrayOf("Guitar","Ukulele","Banjo","Bass")

    val standardTuning:TuningMode = TuningMode("Guitar","standardTuning","E2","A2","D3","G3","B3","E4",82,110,147,196,247,330)
    val dropDTuning:TuningMode = TuningMode("Guitar","dropDTuning","D2","A2","D3","G3","B3","E4",73,110,147,196,247,330)
    val dropATuning:TuningMode = TuningMode("Guitar","dropATuning","A1","A2","D3","G3","B3","E4",55,110,147,196,247,330)
    val openATuning:TuningMode = TuningMode("Guitar","openATuning","E2","A2","C#3","E3","A3","E4",82,110,139,165,220,330)
    val openBTuning:TuningMode = TuningMode("Guitar","openBTuning","B1","F#2","B2","F#3","B3","D#4",62,93,123,185,247,311)
    val openFTuning:TuningMode = TuningMode("Guitar","openFTuning","C2","F2","C3","F3","A3","F4",65,87,131,175,220,349)

    val tuningArray = listOf(standardTuning,dropDTuning,dropATuning,openATuning,openBTuning,openFTuning)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuning)

        val instrumentSpinner = findViewById<Spinner>(R.id.instrumentSpinner)
        val instrumentArrayAdapter = ArrayAdapter(this,R.layout.custom_spinner_item,instrumentArray)
        val collapseTuningDialogBtn = findViewById<ImageButton>(R.id.collapseTuningDialogBtn)
        val recyclerV = findViewById<RecyclerView>(R.id.recyclerView)
        instrumentSpinner.adapter = instrumentArrayAdapter

        recyclerV.layoutManager = LinearLayoutManager(this)
        recyclerV.adapter = RecyclerAdapter(tuningArray)

        collapseTuningDialogBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}