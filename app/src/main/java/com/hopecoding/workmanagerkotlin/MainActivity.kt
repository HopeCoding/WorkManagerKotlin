package com.hopecoding.workmanagerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.sql.Ref
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = Data.Builder().putInt("intKey", 1).build()


        val constraints = Constraints.Builder()
            // .setRequiredNetworkType(NetworkType.CONNECTED) internet bağlı olduğunda.
            .setRequiresCharging(false).build()

        //1 Kere istek kullanıldığında
        /*
        val myWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<RefreshDb>()
            .setConstraints(constraints)
            .setInputData(data)
            // .setInitialDelay(5, TimeUnit.HOURS)
            //.addTag("myTag")
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

         */

        // Bunu en az 15 dk yapabilirsiniz.
        // Daha az yapmak istiyorsanız 15 saniye gibi felan başka handler vs. gibi şeyler kullanın
        val myWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<RefreshDb>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(data)
                .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this,
            Observer {
                if (it.state == WorkInfo.State.RUNNING) {
                    println("running")
                } else if (it.state == WorkInfo.State.FAILED) {
                    println("failed")
                } else if (it.state == WorkInfo.State.SUCCEEDED) {
                    println("succeeded")
                }
            })
        //WorkManager.getInstance().cancelAllWork() Tüm işleri iptal et.

        //Chaining
        //WorkManager bu işi yap sonra bu işi yap diyip birbirine zincirleyebiliyorsun.

        /*
        val OneTimeRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDb>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).beginWith(OneTimeRequest)
            .then(OneTimeRequest)
            .then(OneTimeRequest)
            .enqueue()
        
         */

    }
}