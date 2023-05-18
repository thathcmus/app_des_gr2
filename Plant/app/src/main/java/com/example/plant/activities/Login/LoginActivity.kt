package com.example.plant.activities.Login

import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.plant.activities.ForgotPassword.ForgotAccountActivity
import com.example.plant.activities.Home.HomeActivity
import com.example.plant.R
import com.example.plant.activities.RegisterAccount.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.btnForgot
import kotlinx.android.synthetic.main.activity_login.btnLogin
import kotlinx.android.synthetic.main.activity_login.btnRegister
import kotlinx.android.synthetic.main.activity_login.checkboxRemember
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //hide action bar
        supportActionBar?.hide()
        init()
        listenerEvent()

    }
    private fun listenerEvent () {
        btnLogin.setOnClickListener() {
            login()
        }
        btnRegister.setOnClickListener() {
            register()
        }
        btnForgot.setOnClickListener() {
            forgot()
        }
    }
    private fun init() {
        val savedAccount = getSharedPreferences("savedAccount", Context.MODE_PRIVATE)
        val isRemembered = savedAccount.getBoolean("isRemembered", false)
        val savedEmail = savedAccount.getString("email", "")
        val savedPassword = savedAccount.getString("password", "")
        checkboxRemember.isChecked = isRemembered
        if (isRemembered) {
            // Đặt lại email và password đã lưu trữ (nếu có)
            etEmail.setText(savedEmail)
            etPassword.setText(savedPassword)
        }
    }
    private fun login(){
        mAuth = FirebaseAuth.getInstance()
        val emailStr = etEmail.text.toString().trim()
        val passStr = etPassword.text.toString().trim()
        val stillLoginPre = getSharedPreferences("stillLogin", Context.MODE_PRIVATE)
        val savedAccount = getSharedPreferences("savedAccount", Context.MODE_PRIVATE)

        if(TextUtils.isEmpty(emailStr)){
            Toast.makeText(this, "Enter your email!", Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(passStr)){
            Toast.makeText(this, "Enter your password!", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth.signInWithEmailAndPassword(emailStr, passStr)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful()){
                    Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)

                    val editor = stillLoginPre.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    if (checkboxRemember.isChecked) {
                        val editor = savedAccount.edit()
                        editor.putBoolean("isRemembered", true)
                        editor.putString("email", emailStr)
                        editor.putString("password", passStr)
                        editor.apply()
                    } else {
                        val editor = savedAccount.edit()
                        editor.putBoolean("isRemembered", false)
                        editor.remove("email")
                        editor.remove("password")
                        editor.apply()
                    }
                }
                else
                {
                    Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun forgot() {
        val intent = Intent(this, ForgotAccountActivity::class.java)
        startActivity(intent)
    }


}