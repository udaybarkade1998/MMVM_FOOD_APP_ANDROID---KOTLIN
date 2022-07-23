package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.model.UserModel

@Dao
interface LoginDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(user: UserModel)

    @Query("SELECT * FROM users where pin=:userPin")
     fun getLoginData(userPin: Int):LiveData<UserModel>

}