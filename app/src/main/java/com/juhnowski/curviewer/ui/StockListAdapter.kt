package com.juhnowski.curviewer.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.juhnowski.curviewer.R
import com.juhnowski.curviewer.data.Rec


class StockHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var name:TextView
    var volume:TextView
    var amount:TextView

    init{
        name = itemView.findViewById(R.id.tvName)
        volume = itemView.findViewById(R.id.tvVolume)
        amount = itemView.findViewById(R.id.tvAmount)
    }

    fun bind(rec:Rec){
        name.setText(rec.name)
        volume.setText("${rec.volume}")
        amount.setText("%.2f".format(rec.price.amount))
    }
}

class StockListAdapter(private val context: Context, private val recList: List<Rec>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view:View
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock,parent,false)
        return StockHolder(view)
    }

    override fun getItemCount(): Int {
        return recList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        val rec = recList[position]
        val holder: RecyclerView.ViewHolder = p0
        (holder as StockHolder).bind(rec)
    }


}