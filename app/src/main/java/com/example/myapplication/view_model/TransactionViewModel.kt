package com.example.myapplication.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.FoodAppDatabase
import com.example.myapplication.model.TransactionModel
import com.example.myapplication.model.UserModel
import com.example.myapplication.repository.LoginRepository
import com.example.myapplication.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application): AndroidViewModel(application) {

    var transactionRepository: TransactionRepository

    var liveTransactionData:LiveData<List<TransactionModel>>?=null

    init {
        val dao = FoodAppDatabase.getDatabase(application).getTransactionDAO()
        transactionRepository = TransactionRepository(dao)
    }

    fun transactionInsert(transactionModel: TransactionModel) = viewModelScope.launch {
        transactionRepository.insert(transactionModel)
    }

    fun getTransactionData(userId:Int) = viewModelScope.launch {
        liveTransactionData = transactionRepository.getAllTransactions(userId)
    }


}