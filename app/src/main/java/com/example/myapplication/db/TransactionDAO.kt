package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.model.TransactionModel
import com.example.myapplication.model.UserModel

@Dao
interface TransactionDAO {

    @Insert(onConflict = IGNORE)
    suspend fun insert(transaction:TransactionModel)

    @Query("SELECT * FROM transactions where userId=:userId")
     fun getTransactionData(userId: Int):LiveData<List<TransactionModel>>

}