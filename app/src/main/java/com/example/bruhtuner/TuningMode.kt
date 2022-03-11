package com.example.bruhtuner

class TuningMode {

    var instrumentName: String = ""
    var tuningModeName: String = ""

    var firstStringName: String = ""
    var secondStringName: String = ""
    var thirdStringName: String = ""
    var fourthStringName: String = ""
    var fifthStringName: String = ""
    var sixthStringName: String = ""

    var firstStringFrequency: Int = 0
    var secondStringFrequency: Int = 0
    var thirdStringFrequency: Int = 0
    var fourthStringFrequency: Int = 0
    var fifthStringFrequency: Int = 0
    var sixthStringFrequency: Int = 0

    constructor(
        instrumentName: String,
        tuningModeName:String,
        firstStringName: String,
        secondStringName: String,
        thirdStringName: String,
        fourthStringName: String,
        fifthStringName: String,
        sixthStringName: String,
        firstStringFrequency:Int,
        secondStringFrequency: Int ,
        thirdStringFrequency: Int,
        fourthStringFrequency: Int ,
        fifthStringFrequency: Int,
        sixthStringFrequency: Int
    ){
        this.instrumentName = instrumentName
        this.tuningModeName = tuningModeName

        this.firstStringName = firstStringName
        this.secondStringName = secondStringName
        this.thirdStringName = thirdStringName
        this.fourthStringName = fourthStringName
        this.fifthStringName = fifthStringName
        this.sixthStringName = sixthStringName

        this.firstStringFrequency = firstStringFrequency
        this.secondStringFrequency = secondStringFrequency
        this.thirdStringFrequency = thirdStringFrequency
        this.fourthStringFrequency = fourthStringFrequency
        this.fifthStringFrequency = fifthStringFrequency
        this.sixthStringFrequency = sixthStringFrequency
    }
}