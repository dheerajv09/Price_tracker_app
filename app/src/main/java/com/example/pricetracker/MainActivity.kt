package com.example.pricetracker

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var stratTrackingPrice: Button
    lateinit var stopTrackingPrice: Button
    lateinit var productUrl: EditText
    lateinit var productName: TextView
    lateinit var expectedPrice: EditText
    lateinit var actualPrice: TextView
    lateinit var fetchProductDetails: FetchProductDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productUrl = findViewById(R.id.url_editText)
        expectedPrice = findViewById(R.id.price_editText)
        actualPrice = findViewById(R.id.product_price_textView)
        stratTrackingPrice = findViewById(R.id.track_price_button)
        productName = findViewById(R.id.product_name_textView)
        stopTrackingPrice = findViewById(R.id.stop_trackingPrice_button)

        stratTrackingPrice.setOnClickListener(this)
        stopTrackingPrice.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val sharedPreferences = ProductSharedPreferences(this)
        if (p0 == stratTrackingPrice) {
            fetchProductDetails = object : FetchProductDetails(applicationContext){
                override fun onPostExecute(result: ArrayList<String>?) {
                     super.onPostExecute(result)
                    actualPrice.text = result!![0]
                    productName.text = result!![1]

                }
            }
            fetchProductDetails.execute()

            
            sharedPreferences.saveData(
                // Saves product url, expectedPrice and wheather is tracking active and save in shared Preferences
                url = productUrl.text.toString(),
                name = productName.text.toString(),
                expectedPrice = expectedPrice.text.toString().toInt(),
                isTracking = true
            )

            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val task =
                PeriodicWorkRequest.Builder(
                    PriceTrackerService::class.java,
                    10,
                    TimeUnit.MINUTES
                ).setConstraints(constraints)
                    .build()

            // Start work manager
            WorkManager.getInstance(this).enqueue(task)
        }

        if(p0 == stopTrackingPrice){
            sharedPreferences.saveData(
                url = productUrl.text.toString(),
                name = productName.text.toString(),
                expectedPrice = expectedPrice.text.toString().toInt(),
                isTracking = false
            )
            // Cancel work manager
            WorkManager.getInstance(this).cancelAllWork()


        }
    }
}