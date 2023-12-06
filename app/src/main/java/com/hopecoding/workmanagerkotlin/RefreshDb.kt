package com.hopecoding.workmanagerkotlin

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDb(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val getData = inputData
        val myNumber = getData.getInt("intKey", 0)
        refreshDatabase(myNumber)

        return Result.success()
    }

    private fun refreshDatabase(myNumber: Int) {
        val sharedPreferences =
            context.getSharedPreferences("com.hopecoding.workmanagerkotlin", Context.MODE_PRIVATE)
        var sendNumber = sharedPreferences.getInt("sendNumber", 0)
        sendNumber = sendNumber + myNumber
        println(sendNumber)
        sharedPreferences.edit().putInt("sendNumber", sendNumber).apply()
    }
}