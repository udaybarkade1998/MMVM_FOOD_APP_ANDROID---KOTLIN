package com.example.myapplication.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ItemAdapter
import com.example.myapplication.adapter.TransactionAdapter
import com.example.myapplication.model.ProductModel
import com.example.myapplication.model.TransactionModel
import com.example.myapplication.view.LoginActivity
import com.example.myapplication.view_model.TransactionViewModel
import java.util.*


class FragmentTransaction : Fragment() {

    lateinit var transactionViewModel: TransactionViewModel
    private var adapter: TransactionAdapter? = null

    lateinit var searchView:EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_transaction, container, false)

        var newOrder: Button = view.findViewById(R.id.btnewOrder)
        newOrder.visibility = if (LoginActivity.userModel.type == 1) View.GONE else View.VISIBLE

        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]
        transactionViewModel.getTransactionData(LoginActivity.userModel.id)
        transactionViewModel.liveTransactionData?.observe(viewLifecycleOwner) {
            if (it != null) {
                var recyclerView: RecyclerView = view.findViewById(R.id.rvTransaction)
                recyclerView.layoutManager = GridLayoutManager(view.context, 2)
                adapter=TransactionAdapter(it)
                recyclerView.adapter = adapter
            }
        }

        newOrder.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentRoot, FragmentMenu()).commit()
        }


        searchView = view.findViewById(R.id.searchTransaction)

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

        return view
    }

    fun filter(text: String) {

        transactionViewModel.liveTransactionData?.observe(viewLifecycleOwner) {
            if (it != null) {
                var text = text
                var temp = ArrayList<TransactionModel>()

                text = text.trim { it <= ' ' }.lowercase(Locale.getDefault())
                for (item in it) {
                    if (item.id.toString().contains(text)
                    ) {
                        temp.add(item)
                    }
                }
                //update recyclerview
                adapter!!.updateList(temp)
            }
        }

    }

}