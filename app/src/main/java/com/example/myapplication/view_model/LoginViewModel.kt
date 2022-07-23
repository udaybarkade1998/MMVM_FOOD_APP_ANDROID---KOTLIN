package com.example.myapplication.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.FoodAppDatabase
import com.example.myapplication.model.UserModel
import com.example.myapplication.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application) {

    var loginRepository: LoginRepository

    var liveUserData:LiveData<UserModel>?=null

    init {
        val dao = FoodAppDatabase.getDatabase(application).getLoginDAO()
        loginRepository = LoginRepository(dao)
    }

    fun userInsert(userModel: UserModel) = viewModelScope.launch {
        loginRepository.insert(userModel)
    }

    fun getUserData(userPin:Int) = viewModelScope.launch {
        liveUserData = loginRepository.getUserData(userPin)
    }


}