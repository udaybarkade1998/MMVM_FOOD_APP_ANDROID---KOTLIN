package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.CartItemModel
import com.example.myapplication.view.LandingActivity
import com.example.myapplication.view.fragments.FragmentCart
import com.google.android.material.imageview.ShapeableImageView

class CartAdapter(private val keyList :List<Int>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    private val cartList: HashMap<Int, CartItemModel> = LandingActivity.cartItem

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cart_item_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.name.text = cartList[keyList[i]]!!.name
        viewHolder.price.text = cartList[keyList[i]]!!.price.toString()
        viewHolder.quantity.text = cartList[keyList[i]]!!.quantity.toString()

        viewHolder.image.setImageResource(cartList[keyList[i]]!!.item_image)

        var count:Int=viewHolder.quantity.text.toString().toInt()

        var itemCost =count * cartList[keyList[i]]!!.price


        setPaymentInfo(itemCost)
        var price =-cartList[keyList[i]]!!.price

        viewHolder.remove.setOnClickListener {
            count=viewHolder.quantity.text.toString().toInt()
            count--



            if(count == 0) {
                notifyItemRemoved(i)
                LandingActivity.cartItem.remove(keyList[i])
            }
            else {
                viewHolder.quantity.text = count.toString()
                itemCost = count * cartList[keyList[i]]!!.price
                viewHolder.price.text = "$itemCost$"

            }
            setPaymentInfo(price)
        }



        viewHolder.price.text = "$itemCost$"

        viewHolder.add.setOnClickListener {
            count=viewHolder.quantity.text.toString().toInt()
            if(count<10) {
                count++
                viewHolder.quantity.text = count.toString()
                itemCost = count * cartList[keyList[i]]!!.price
                viewHolder. price.text = "$itemCost$"

                setPaymentInfo(cartList[keyList[i]]!!.price)
            }
        }


    }

    private fun setPaymentInfo(itemCost: Float) {
        FragmentCart.subtotal +=itemCost
        FragmentCart.tvSubTotal!!.text = "${FragmentCart.subtotal.toString()}$"

        FragmentCart.total=FragmentCart.subtotal+FragmentCart.subtotal/100*FragmentCart.tax

        FragmentCart.tvTotal!!.text=FragmentCart.total.toString()
    }


    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SetTextI18n")
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ShapeableImageView
        var name: TextView
        var price: TextView
        var add:TextView
        var remove:TextView
        var quantity:TextView

        init {
            image = itemView.findViewById(R.id.cart_item_image)
            name = itemView.findViewById(R.id.cart_item_name)
            price = itemView.findViewById(R.id.cartItem_price)
            add = itemView.findViewById(R.id.increaseCount)
            remove = itemView.findViewById(R.id.decreaseCount)
            quantity = itemView.findViewById(R.id.itemCount)




        }

    }
}