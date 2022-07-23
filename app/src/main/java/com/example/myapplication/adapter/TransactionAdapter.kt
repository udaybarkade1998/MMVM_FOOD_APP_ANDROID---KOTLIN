package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.ProductModel
import com.example.myapplication.model.TransactionModel

class TransactionAdapter(
    private var tranList: List<TransactionModel>
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    var selectedPosition = -1;

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.transaction_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.name.text = tranList[i].name
        viewHolder.price.text = "${tranList[i].cost}$"
        viewHolder.time.text = tranList[i].time
        viewHolder.id.text = tranList[i].id.toString()


    }


    override fun getItemCount(): Int {
        return tranList.size
    }

    fun updateList(list:ArrayList<TransactionModel> ){
        tranList = list;
        notifyDataSetChanged();
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var price: TextView
        var time: TextView
        var id: TextView

        init {
            time = itemView.findViewById(R.id.tran_time)
            name = itemView.findViewById(R.id.tran_customerName)
            price = itemView.findViewById(R.id.tran_price)
            id = itemView.findViewById(R.id.tran_id)
        }

    }
}