package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.db.LoginDAO
import com.example.myapplication.db.TransactionDAO
import com.example.myapplication.model.TransactionModel
import com.example.myapplication.model.UserModel

class TransactionRepository(private val transactionDAO: TransactionDAO) {

    suspend fun insert(transactionModel: TransactionModel){
        transactionDAO.insert(transactionModel)
    }

     fun getAllTransactions(userId:Int):LiveData<List<TransactionModel>>{
        return transactionDAO.getTransactionData(userId)
    }

}