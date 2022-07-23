package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
class TransactionModel(
    @ColumnInfo(name = "userId")
    val userId:Int,
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "email")
    val email:String,
    @ColumnInfo(name = "phone")
    val phone:String,
    @ColumnInfo(name = "time")
    val time:String,
    @ColumnInfo(name = "cost")
    val cost:Float,

) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}