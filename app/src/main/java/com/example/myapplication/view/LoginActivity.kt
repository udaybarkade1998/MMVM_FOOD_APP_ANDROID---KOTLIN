package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.model.UserModel
import com.example.myapplication.view_model.LoginViewModel
import kotlinx.android.synthetic.main.activity_new.*

class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel

    companion object {
        var userName = ""
        var userType = 0
        lateinit var userModel: UserModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        loginViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LoginViewModel::class.java)


        //this is for only sample users added in database
        loginViewModel.userInsert(UserModel("Server user 1", 1, 1234))
        loginViewModel.userInsert(UserModel("Manager User 1", 2, 4321))


        btLogin.setOnClickListener(View.OnClickListener {

            loginViewModel.getUserData(etLoginPin.text.toString().toInt())

            loginViewModel.liveUserData!!.observe(this) {
                if (it == null) {
                    etLoginPin.error = "Wrong Credentials"

                } else {
                    userModel=it
                    switchToLandingPage()
                }
            }
        })


    }

    private fun switchToLandingPage() {
        startActivity(Intent(this@LoginActivity, LandingActivity::class.java))
        finish()
    }
}