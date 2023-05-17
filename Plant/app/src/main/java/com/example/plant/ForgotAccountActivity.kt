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

class ForgotAccountActivity : AppCompatActivity() {
    private lateinit var emailEdit: EditText
    private lateinit var btnReset: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_account)
        mAuth = FirebaseAuth.getInstance()

        emailEdit = findViewById(R.id.email)

        btnReset = findViewById(R.id.btnReset)

        btnReset.setOnClickListener() {
            reset()
        }
    }
    private fun reset() {
        val emailStr = emailEdit.text.toString().trim()
        if(TextUtils.isEmpty(emailStr)){
            Toast.makeText(this, "Nhập địa chỉ email!", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            emailEdit.error = "Email sai định dạng"
            emailEdit.requestFocus()
            return
        }
        mAuth.sendPasswordResetEmail(emailStr)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful()){
                    Toast.makeText(this, "Kiểm tra email và reset mật khẩu!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "Thất bại!", Toast.LENGTH_SHORT).show()
                    emailEdit.requestFocus()
                }

            }
    }
}