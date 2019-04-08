package com.juhnowski.curviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.juhnowski.curviewer.service.ApiFactory
import com.juhnowski.curviewer.service.StockApi
import com.juhnowski.curviewer.storage.Storage.STOCK
import com.juhnowski.curviewer.ui.StockListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    var adapter:StockListAdapter? = null
    val service:StockApi = ApiFactory.stockApi

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pb = findViewById<ProgressBar>(R.id.pb)
        pb.isIndeterminate = true
        GlobalScope.launch(Dispatchers.Main) {
            repeat(Int.MAX_VALUE) {
                pb.visibility = View.VISIBLE
                val getRequest = service.getStock()
                try {
                    val response = getRequest.await()
                    if (response.isSuccessful) {
                        STOCK = response.body()!!
                        val recycler = findViewById<View>(R.id.recycler_view_stock) as RecyclerView?

                        adapter = StockListAdapter(this@MainActivity, STOCK!!.stock)
                        recycler!!.layoutManager =
                            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                        recycler!!.adapter = adapter

                        Log.d(TAG, "stock=${STOCK}")
                        adapter!!.notifyDataSetChanged()
                        println("adapter count = ${adapter!!.getItemCount()}")
                        pb.visibility = View.INVISIBLE
                    } else {
                        Log.d(TAG, "Not successfull ${response.errorBody().toString()}")
                    }
                } catch (e: Exception) {
                    Log.d(TAG, e.message)
                }
                delay(15000)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {

            R.id.sync_options -> {
                GlobalScope.launch(Dispatchers.Main) {
                    pb.visibility = View.VISIBLE
                        val getRequest = service.getStock()
                        try {
                            val response = getRequest.await()
                            if (response.isSuccessful) {
                                STOCK = response.body()!!
                                val recycler = findViewById<View>(R.id.recycler_view_stock) as RecyclerView?

                                adapter = StockListAdapter(this@MainActivity, STOCK!!.stock)
                                recycler!!.layoutManager =
                                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                                recycler!!.adapter = adapter

                                Log.d(TAG, "stock=${STOCK}")
                                adapter!!.notifyDataSetChanged()
                                println("adapter count = ${adapter!!.getItemCount()}")
                                pb.visibility = View.INVISIBLE
                            } else {
                                Log.d(TAG, response.errorBody().toString())
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, e.message)
                        }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
