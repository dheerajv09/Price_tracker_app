package com.example.pricetracker


import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters


class PriceTrackerService(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    val fetchProductDetails: FetchProductDetails = FetchProductDetails(applicationContext)
    override fun doWork(): Result {

        return try {
            fetchProductDetails.execute()
            Result.success()
        } catch (e: Exception) {
            println("------$e")
            Result.failure()
        }
    }

    override fun onStopped() {
        fetchProductDetails.cancel(true)
        super.onStopped()
    }
}
