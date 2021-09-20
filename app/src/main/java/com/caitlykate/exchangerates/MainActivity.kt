package com.caitlykate.exchangerates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var doc : Document        //принимает всю страницу, можем вытаскивать разные части
    lateinit var rcView: RecyclerView
    private var adapter = MyAdapter()
    var list = ArrayList<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){

        rcView = findViewById(R.id.rcView)
        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(this)

        rcView.adapter = adapter
        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            getWeb()
        }


    }

    private fun getWeb(){
        try {
            doc = Jsoup.connect("https://www.cbr.ru/currency_base/daily/").get()
            val allTable = doc.getElementsByClass("data").get(0).children().get(0)

            for (n in listOf(1, 11, 12, 15, 29, 32, 34)){

                    val currencyInfo = allTable.children().get(n)
                    val row = ListItem(
                        currencyInfo.child(1).text(),
                        currencyInfo.child(3).text(),
                        currencyInfo.child(4).text().dropLast(2)
                    )
                    list.add(row)
                    Log.d("MyLog", "Text: $row")

            }

        }
        catch (e: Exception) {
            Log.d("MyLog", "Exception: ${e.message}")
        }

        runOnUiThread {
            adapter.updateAdapter(list)

            val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            val currentDate = sdf.format(Date())

            val tvTitle: TextView = findViewById(R.id.tvTitle)
            tvTitle.setText("Курсы валют к рублю РФ \n на $currentDate")



        }

    }
}