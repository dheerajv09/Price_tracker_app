package com.example.pricetracker

import android.content.Context
import android.os.AsyncTask
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

open class FetchProductDetails(private val context: Context): AsyncTask<Void, Void, ArrayList<String>>() {
     var sharedPreferences: ProductSharedPreferences = ProductSharedPreferences(context)
    val productDetails: ArrayList<String> = ArrayList()

    override fun doInBackground(vararg p0: Void?): ArrayList<String> {

        try {

            val url: String = "https://www.amazon.in/HP-Processor-14-inch-i5-1035G1-cs3009TU/dp/B087S265GM/ref=sr_1_1?dchild=1&pf_rd_i=1375424031&pf_rd_m=A1VBAL9TL5WCBF&pf_rd_p=8e0e6a5e-6e7a-4c21-be1c-f245dcb11e66&pf_rd_r=1G7X2EW9CV987JQR041D&pf_rd_s=merchandised-search-3&pf_rd_t=101&qid=1597086743&smid=A2JRT5V3UPLILP&sr=8-1"

            //productSharedPreferences.getUrl()
            val document: Document = Jsoup.connect(url).get()

            val price = document.getElementById("priceblock_ourprice").text()
            val name = document.getElementById("productTitle").text()

            if (price.toInt() <= sharedPreferences.getExpectedPrice()) {
                val sendMail:SendMail = SendMail("dheerajv634@gmail.com", "Amazon: product price lowered", "Your product ${sharedPreferences.getProductName()} price has felt down to your expected price i.e ${sharedPreferences.getExpectedPrice()}. Product url ${sharedPreferences.getProductUrl()}")
                sendMail.execute()
            }

            productDetails.add(price)
            productDetails.add(name)

        } catch (e: Exception) {
            println("------$e")
        }
        return productDetails
    }

    override fun onPostExecute(result: ArrayList<String>?) {
        super.onPostExecute(result)
    }

    override fun onCancelled() {
        super.onCancelled()
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }
}