package com.example.myapplication.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.CartAdapter
import com.example.myapplication.model.TransactionModel
import com.example.myapplication.view.LandingActivity
import com.example.myapplication.view.LoginActivity
import com.example.myapplication.view_model.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.*


class FragmentCart : Fragment() {


    var addToCart: LinearLayout? = null
    lateinit var transactionViewModel: TransactionViewModel

    lateinit var confirmButton: Button

    companion object {
        var subtotal: Float = 0.0f
        val tax: Float = 8.25f;
        var total: Float = 0.0f

        lateinit var tvTotal: TextView
        lateinit var tvTax: TextView
        lateinit var tvSubTotal: TextView

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)


        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        var keyList = mutableListOf<Int>()

        for ((key, value) in LandingActivity.cartItem)
            keyList.add(key)

        var toGoButton: Button = view.findViewById(R.id.btTogo)
        toGoButton.visibility = if (LoginActivity.userModel.type == 1) GONE else VISIBLE

        tvTotal = view.findViewById(R.id.tvTotalVal)
        tvTax = view.findViewById(R.id.tvTaxVal)
        tvSubTotal = view.findViewById(R.id.tvSubTotalVal)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvCartItems)

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        recyclerView.adapter = CartAdapter(keyList)

        confirmButton = view.findViewById(R.id.btConfirm)
        confirmButton.setOnClickListener {
            if (subtotal > 0)
                confirmOrder("Guest", "", "")
            else
                Toast.makeText(context, "Please add Item to cart", Toast.LENGTH_LONG).show()
        }

        toGoButton.setOnClickListener {
            openDilaog(view)
        }


        return view
    }

    private fun openDilaog(view: View) {
        val mDialog: Dialog
        mDialog = Dialog(view.context)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.togo_alert)
        mDialog.setCancelable(false)

        val ok: TextView = mDialog.findViewById(R.id.btConfirmTogo)
        ok.setOnClickListener {

            var name: EditText = mDialog.findViewById(R.id.et_userName)
            var email: EditText = mDialog.findViewById(R.id.et_email)
            var phone: EditText = mDialog.findViewById(R.id.et_phone)


            confirmOrder(
                if (name.text.toString().trim().isEmpty()) "Togo" else name.text.toString(),
                email.text.toString().trim(),
                phone.text.toString().trim()
            )

            mDialog.cancel()

        }

        val cancel: TextView = mDialog.findViewById<View>(R.id.btCancelTogo) as TextView
        cancel.setOnClickListener { mDialog.cancel() }
        mDialog.show()
    }

    private fun confirmOrder(name: String, email: String, phone: String) {

        val formatter = SimpleDateFormat("hh:mm a")
        val time = formatter.format(Date())

        transactionViewModel.transactionInsert(
            TransactionModel(
                LoginActivity.userModel.id,
                name,
                email,
                phone,
                time,
                total
            )
        )
        LandingActivity.cartItem.clear()
        subtotal = 0.0f
        total = 0.0f

        confirmButton.isEnabled = false


        parentFragmentManager.beginTransaction().replace(R.id.fragmentRoot, FragmentTransaction())
            .commit()

    }


}


