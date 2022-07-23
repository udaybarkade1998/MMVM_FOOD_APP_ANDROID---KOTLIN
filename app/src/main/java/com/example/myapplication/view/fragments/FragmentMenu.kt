package com.example.myapplication.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.R.color
import com.example.myapplication.adapter.ItemAdapter
import com.example.myapplication.model.CartItemModel
import com.example.myapplication.model.ProductModel
import com.example.myapplication.view.LandingActivity
import kotlinx.android.synthetic.main.fragment_cart.*
import java.util.*


class FragmentMenu : Fragment() {

    private var adapter: ItemAdapter? = null

    var addToCart: LinearLayout? = null

    lateinit var searchView: EditText
    lateinit var itemList: ArrayList<ProductModel>

    lateinit var radioGroup:RadioGroup
    lateinit var radioButton: RadioButton

    companion object {
        var selectedItemId: Int = -1
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        var recyclerView: RecyclerView = view.findViewById(R.id.rvSearchItems)

        //category selection
        radioGroup = view.findViewById(R.id.radioGroup)

        radioGroup.setOnClickListener(){

            var id = radioGroup.checkedRadioButtonId

            radioButton = view.findViewById(id)
            radioButton.setBackgroundColor(color.tomato)
            radioButton.text ="changed"
        }


        //add to cart layout
        addToCart = view.findViewById(R.id.llAddToCart)

        //adding list of product as we don't have admin panel
        itemList = ArrayList<ProductModel>()

        itemList.add(ProductModel(12, "Chips", 10f, R.drawable.chips1, category = "chips"))
        itemList.add(ProductModel(13, "Chips 2", 12f, R.drawable.chips2, category = "chips"))
        itemList.add(ProductModel(1, "Burger", 10f, R.drawable.ultimateburger, category = "burger"))
        itemList.add(ProductModel(3, "Burger3", 15f, R.drawable.burger1, category = "burger"))
        itemList.add(ProductModel(7, "sandwich", 10f,R.drawable.sandwich1 , category = "sandwich"))
        itemList.add(ProductModel(2, "Burger1", 12f, R.drawable.burger_cheez, category = "burger"))
        itemList.add(ProductModel(9, "sandwich", 15f, R.drawable.sandwich2, category = "sandwich"))
        itemList.add(ProductModel(14, "Chips 3", 15f, R.drawable.chips3, category = "chips"))


        addToCart!!.setOnClickListener(View.OnClickListener {

            if (selectedItemId != -1) {
                Toast.makeText(view.context, "Added to Cart$selectedItemId", Toast.LENGTH_LONG)
                    .show()
                Log.e("Added", "" + selectedItemId)


                if (LandingActivity.cartItem.contains(selectedItemId)) {
                    var quantity: Int = LandingActivity.cartItem[selectedItemId]!!.quantity++
                    quantity++

                    LandingActivity.cartItem.replace(
                        selectedItemId,
                        CartItemModel(
                            selectedItemId,
                            itemList[selectedItemId].name,
                            price = itemList[selectedItemId].price,
                            quantity = quantity,
                            item_image=itemList[selectedItemId].imagePath,
                        )
                    )
                } else {
                    LandingActivity.cartItem[selectedItemId] = CartItemModel(
                        selectedItemId,
                        itemList[selectedItemId].name,
                        price = itemList[selectedItemId].price,
                        quantity = 1,
                        item_image=itemList[selectedItemId].imagePath,
                    )
                }
            }


            LandingActivity.cartItem.forEach {
                Log.e("${it.key}", "${it.value.name} Count = ${it.value.quantity}")
            }

        })


        searchView = view.findViewById(R.id.searchView)

        searchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {
                    filter(s.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })


        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        adapter = ItemAdapter(itemList)
        recyclerView.adapter = adapter



        return view
    }

    fun filter(text: String) {
        var text = text
        var temp = ArrayList<ProductModel>()

        text = text.trim { it <= ' ' }.lowercase(Locale.getDefault())
        for (item in itemList) {
            if (item.name.toLowerCase().contains(text)
            ) {
                temp.add(item)
            }
        }
        //update recyclerview
        adapter!!.updateList(temp)
    }


}