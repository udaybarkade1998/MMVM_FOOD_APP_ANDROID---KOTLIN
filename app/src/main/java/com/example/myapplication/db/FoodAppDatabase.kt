package com.example.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.TransactionModel
import com.example.myapplication.model.UserModel

@Database(entities = [UserModel::class,TransactionModel::class], version = 4, exportSchema = false)
abstract class FoodAppDatabase : RoomDatabase(){

    abstract fun getLoginDAO():LoginDAO
    abstract fun getTransactionDAO():TransactionDAO

    companion object{
        private var INSTANCE:FoodAppDatabase?=null

        fun getDatabase(context: Context):FoodAppDatabase{
            return INSTANCE?: synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodAppDatabase::class.java,
                    "foodAppDatabase"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}