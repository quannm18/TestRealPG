package com.example.testrealpg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.math.log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val motionProcess: CustomProgressBar2 by lazy { findViewById<CustomProgressBar2>(R.id.motionProcess) }
    private val btnABC: Button by lazy { findViewById<Button>(R.id.btnABC) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var progress = 100f
//        btnABC.setOnClickListener {
//            runBlocking {
//                for (i in 0..5000){
//                    delay(10)
//                }
//            }
//            motionProcess.setProgress(progress.toFloat())
//            if (progress == 2000f){
//                progress = 0f
//            }
//        }
//        for (i in 0..5000){
//            Thread.sleep(10)
//            motionProcess.setProgress(i.toFloat())
//        }
        val sum = cloneData().sumOf { it }
        val sum1 = cloneData1().sumOf { it }
        val sum2 = cloneData3().sumOf { it }


    }

    fun cloneData() : MutableList<Int> {
        val mList1 = mutableListOf<Int>()
        for (i in 0..50){
            mList1.add(Random.nextInt(10))
        }
        return mList1
    }

    fun cloneData1() : MutableList<Int> {
        val mList2 = mutableListOf<Int>()
        for (i in 0..50){
            mList2.add(Random.nextInt(10))
        }
        return mList2
    }

    fun cloneData3() : MutableList<Int> {
        val mList2 = mutableListOf<Int>()
        for (i in 0..50){
            mList2.add(Random.nextInt(10))
        }
        return mList2
    }
}