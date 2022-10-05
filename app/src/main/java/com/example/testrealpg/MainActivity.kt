package com.example.testrealpg

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        val total = 100000L
        val free = 20000L

        btnABC.setOnClickListener {
            motionProcess.setProgress(6000,10000L,3000L)
        }
    }

    fun cloneData(): MutableList<Long> {
        val mList1 = mutableListOf<Long>()
        for (i in 0..50) {
            mList1.add(Random.nextLong(1000))
        }
        return mList1
    }

    fun cloneData1(): MutableList<Int> {
        val mList2 = mutableListOf<Int>()
        for (i in 0..50) {
            mList2.add(Random.nextInt(1000))
        }
        return mList2
    }

    fun cloneData3(): MutableList<Int> {
        val mList2 = mutableListOf<Int>()
        for (i in 0..50) {
            mList2.add(Random.nextInt(1000))
        }
        return mList2
    }
}