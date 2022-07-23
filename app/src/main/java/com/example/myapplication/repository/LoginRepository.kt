package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.db.LoginDAO
import com.example.myapplication.model.UserModel

class LoginRepository(private val loginDAO: LoginDAO) {

    suspend fun insert(userModel: UserModel){
        loginDAO.insert(userModel)
    }

     fun getUserData(userPin:Int):LiveData<UserModel>{
        return loginDAO.getLoginData(userPin)
    }

}