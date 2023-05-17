package com.example.plant

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.CheckBox
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEdit: EditText
    private lateinit var passwordEdit: EditText

    private lateinit var checkboxRemember: CheckBox

    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var btnForgot: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        emailEdit = findViewById(R.id.email)
        passwordEdit = findViewById(R.id.password)

        checkboxRemember = findViewById<CheckBox>(R.id.checkboxRemember)
        val savedAccount = getSharedPreferences("savedAccount", Context.MODE_PRIVATE)
        val isRemembered = savedAccount.getBoolean("isRemembered", false)
        val savedEmail = savedAccount.getString("email", "")
        val savedPassword = savedAccount.getString("password", "")

        checkboxRemember.isChecked = isRemembered

        if (isRemembered) {
            // Đặt lại email và password đã lưu trữ (nếu có)
            emailEdit.setText(savedEmail)
            passwordEdit.setText(savedPassword)
        }

        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        btnForgot = findViewById(R.id.btnForgot)


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

    private fun login(){
        val emailStr = emailEdit.text.toString().trim()
        val passStr = passwordEdit.text.toString().trim()
        val stillLoginPre = getSharedPreferences("stillLogin", Context.MODE_PRIVATE)
        val savedAccount = getSharedPreferences("savedAccount", Context.MODE_PRIVATE)

        if(TextUtils.isEmpty(emailStr)){
            Toast.makeText(this, "Nhập địa chỉ email!", Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(passStr)){
            Toast.makeText(this, "Nhập mật khẩu!", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth.signInWithEmailAndPassword(emailStr, passStr)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful()){
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show()
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