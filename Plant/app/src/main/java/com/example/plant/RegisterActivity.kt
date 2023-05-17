package com.example.plant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.util.Patterns

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailEdit: EditText
    private lateinit var passwordedit: EditText
    private lateinit var btnRegister: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()

        emailEdit = findViewById(R.id.email)
        passwordedit = findViewById(R.id.password)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener() {
            register()
        }
    }
    private fun register() {
        val emailStr = emailEdit.text.toString().trim()
        val passStr = passwordedit.text.toString().trim()
        if(TextUtils.isEmpty(emailStr)){
            Toast.makeText(this, "Nhập địa chỉ email!", Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(passStr)){
            Toast.makeText(this, "Nhập mật khẩu!", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            emailEdit.error = "Email sai định dạng"
            emailEdit.requestFocus()
            return
        }
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{11,}\$".toRegex()
        if (!passwordRegex.matches(passStr)) {
            passwordedit.error =
                "Mật khẩu từ 11 ký tự trở lên, bao gồm tổ hợp các chữ cái, chữ số và ký tự đặc biệt @, $, !, %, *, ?, &"
            passwordedit.requestFocus()
            return
        }
        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful()){
                    Toast.makeText(this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "Tạo tài khoản thất bại!", Toast.LENGTH_SHORT).show()
                }

            }
    }
}