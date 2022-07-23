package com.example.myapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.ProductModel
import com.example.myapplication.view.fragments.FragmentMenu
import com.google.android.material.imageview.ShapeableImageView
import java.util.*
import kotlin.collections.ArrayList

class ItemAdapter(
    private var productList: ArrayList<ProductModel>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    var selectedPosition = -1;

    private val filtered= ArrayList<ProductModel>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.name.text = productList[i].name
        viewHolder.price.text = "${productList[i].price.toString()}$"

        viewHolder.image.setImageResource(productList[i].imagePath)


        if (selectedPosition == i) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#9EAFA7A7"))
            // button.setBackgroundResource(R.drawable.carbutton_selected)
            FragmentMenu.selectedItemId = i
        } else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            //  button.setBackgroundColor(Color.parseColor("#000000"))

        }

        viewHolder.itemView.setOnClickListener {
            selectedPosition = i
            notifyDataSetChanged()
        }
    }

    fun updateList(list:ArrayList<ProductModel> ){
        productList = list;
        notifyDataSetChanged();
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ShapeableImageView
        var name: TextView
        var price: TextView

        init {
            image = itemView.findViewById(R.id.item_image)
            name = itemView.findViewById(R.id.item_name)
            price = itemView.findViewById(R.id.item_price)
        }

    }
}