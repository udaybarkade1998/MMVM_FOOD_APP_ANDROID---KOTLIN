package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserModel(
    @ColumnInfo(name = "userName")
    val name:String,
    //user type 1=server , 2=manager
    @ColumnInfo(name = "type")
    val type:Int,
    @ColumnInfo(name = "pin")
    val loginPin:Int
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}