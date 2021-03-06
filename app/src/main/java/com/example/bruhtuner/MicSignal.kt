package com.example.bruhtuner

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

class MicSignal {
    //it'll be useful in the future
    val selectedTuningMode: Int = 0

    val standardTuning:TuningMode = TuningMode("Guitar","standardTuning","E2","A2","D3","G3","B3","E4",82,110,147,196,247,330)
    val dropDTuning:TuningMode = TuningMode("Guitar","dropDTuning","D2","A2","D3","G3","B3","E4",73,110,147,196,247,330)
    val dropATuning:TuningMode = TuningMode("Guitar","dropATuning","A1","A2","D3","G3","B3","E4",55,110,147,196,247,330)
    val openATuning:TuningMode = TuningMode("Guitar","openATuning","E2","A2","C#3","E3","A3","E4",82,110,139,165,220,330)
    val openBTuning:TuningMode = TuningMode("Guitar","openBTuning","B1","F#2","B2","F#3","B3","D#4",62,93,123,185,247,311)
    val openFTuning:TuningMode = TuningMode("Guitar","openFTuning","C2","F2","C3","F3","A3","F4",65,87,131,175,220,349)

    val tuningArray = arrayOf(standardTuning,dropDTuning,dropATuning,openATuning,openBTuning,openFTuning)

    val SOUND_THRESHOLD = 400
    val SAMPLE = 8000
    val MIN_BUFFER = AudioRecord.getMinBufferSize(SAMPLE,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT)

    @SuppressLint("MissingPermission")
    val AUDIO_RECORD = AudioRecord(MediaRecorder.AudioSource.MIC,SAMPLE,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT, MIN_BUFFER)


     fun startTuning(){
        AUDIO_RECORD.startRecording()
    }

     fun isHumming():Boolean{
        val listeningBuffer = ShortArray(32)
        AUDIO_RECORD.read(listeningBuffer, 0, listeningBuffer.size)
        for (buff in listeningBuffer) {
            if (Math.abs(buff.toInt()) >= SOUND_THRESHOLD) return true
        }
        return false
    }
     fun getSignal():ShortArray{
        val buffer = ShortArray(100)
        val amountOfBuffers = 10
        val signal = ShortArray(buffer.size * amountOfBuffers)

        for (i in 0 until amountOfBuffers) {
            AUDIO_RECORD.read(buffer, 0, buffer.size)
            System.arraycopy(buffer, 0, signal, i * 100, buffer.size)
        }

        val fullSignal = ShortArray(SAMPLE)

        val numberOfCopies = fullSignal.size / signal.size
        for (i in 0 until numberOfCopies) System.arraycopy(signal, 0, fullSignal, 0, signal.size)
        return fullSignal
    }
    //This function had been stolen because I'm too stupid to understand univerity math level and I also didn't want to use any external library
     fun calculateDFT(signal: ShortArray): Array<Any> {
        val maxFrequencyMeasured = 400
        val fftArg = Array(maxFrequencyMeasured) {
            DoubleArray(
                maxFrequencyMeasured
            )
        }
        val realisPart = DoubleArray(maxFrequencyMeasured)
        val imaginarisPart = DoubleArray(maxFrequencyMeasured)
        val fftResult = DoubleArray(maxFrequencyMeasured)
        val frequencies = IntArray(maxFrequencyMeasured)

        var maxAmplitude = 0.0
        var highestPeakFrequency = 0

        for (k in 0 until maxFrequencyMeasured) {
            realisPart[k] = 0.0
            imaginarisPart[k] = 0.0
            for (n in 0 until maxFrequencyMeasured) {
                fftArg[k][n] = -2 * Math.PI * k * n / signal.size //bigBufferSize
                realisPart[k] += signal[n] * Math.cos(fftArg[k][n]) //bigBuffer
                imaginarisPart[k] += signal[n] * Math.sin(fftArg[k][n]) //bigBuffer
            }
            fftResult[k] =
                Math.sqrt(realisPart[k] * realisPart[k] + imaginarisPart[k] * imaginarisPart[k])
            frequencies[k] = k

            if (fftResult[k] > maxAmplitude) {
                maxAmplitude = fftResult[k]
                highestPeakFrequency = frequencies[k]
            }
        }
        return arrayOf(fftResult, highestPeakFrequency)
    }
     fun detectFrequencyPeak(fft:DoubleArray):IntArray{
        val maxFrequency = 400
        val peaks = IntArray(4) //sound peaks

        for (k in 90 until maxFrequency - 40) {
            var peak = 0
            for (i in 1..40) {
                if (fft.get(k) > fft.get(k - i) && fft.get(k) > fft.get(k + i)) peak++
            }
            if (peak == 40) {
                if (peaks[0] == 0) peaks[0] = k else {
                    if (peaks[1] == 0) peaks[1] = k else {
                        if (peaks[2] == 0) peaks[2] = k else {
                            peaks[3] = k
                        }
                    }
                }
            }
        }
        return peaks
    }
     fun detectPlayedString(highestPeakFrequency:Int,peaks: IntArray):GuitarStrings{
        if(peaks[0] >= 147.6 && peaks[0]<= 180.4 && peaks[1] >= 221.4 && peaks[1] <= 270.6){
            return GuitarStrings.E
        }
        if(peaks[0] >= 99 && peaks[0] <= 121 && peaks[1] >= 198 && peaks[1] <= 242 && highestPeakFrequency <= 240){
            return GuitarStrings.A
        }
        if(peaks[0] >= 132.3 && peaks[0] <= 161.7 && (peaks[1] >= 264.6 && peaks[1] <= 323.4) || (peaks[1] >= 193.5 && peaks[1] <= 236.5)){
            return GuitarStrings.D
        }
        if((peaks[0] >= 130 || peaks[1] >= 130) && ((highestPeakFrequency >= 177.3 && highestPeakFrequency < 216.7) || (highestPeakFrequency > 354.6 && highestPeakFrequency < 433.4))){
            return GuitarStrings.G
        }
        if((peaks[0] >= 200 || peaks[1] >= 200) && highestPeakFrequency >= 222.3 && highestPeakFrequency <= 271.7){
            return GuitarStrings.B
        }
        if(highestPeakFrequency >= 297 && highestPeakFrequency <= 363){
            return GuitarStrings.secondE
        }
        return GuitarStrings.none
    }
    //In the future I'll have to upgrade this method or even make another class
     fun tuneGuitar(gString:GuitarStrings,highestPeakFrequency: Int,peaks: IntArray):Int{
        if(gString==GuitarStrings.E){
            return tuningArray[selectedTuningMode].firstStringFrequency - highestPeakFrequency
        }
        if(gString==GuitarStrings.A){
            return tuningArray[selectedTuningMode].secondStringFrequency - peaks[0]
        }
        if(gString==GuitarStrings.D){
            return tuningArray[selectedTuningMode].thirdStringFrequency - peaks[0]
        }
        if(gString==GuitarStrings.G){
            if(highestPeakFrequency <240)
                return tuningArray[selectedTuningMode].fourthStringFrequency - highestPeakFrequency;
            else
                return tuningArray[selectedTuningMode].fourthStringFrequency  - highestPeakFrequency/2;
        }
        if(gString==GuitarStrings.B){
            return tuningArray[selectedTuningMode].fifthStringFrequency - highestPeakFrequency
        }
        if(gString==GuitarStrings.secondE){
            return tuningArray[selectedTuningMode].sixthStringFrequency - highestPeakFrequency
        }
        return 0

    }



}