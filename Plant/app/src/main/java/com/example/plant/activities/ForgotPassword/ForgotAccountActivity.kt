package com.example.plant.activities.ForgotPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.util.Patterns
import com.example.plant.R
import com.example.plant.activities.Login.LoginActivity
import com.example.plant.util.ProgressBarLoading
import kotlinx.android.synthetic.main.activity_forgot_account.btnReset
import kotlinx.android.synthetic.main.activity_forgot_account.etForgetEmail

class ForgotAccountActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val loadingDialog = ProgressBarLoading(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_account)
        //hide action bar
        supportActionBar?.hide()
        //get user instance
        mAuth = FirebaseAuth.getInstance()
        //listen reset button event
        btnReset.setOnClickListener() {
            reset()
        }
    }
    private fun reset() {
        val emailStr = etForgetEmail.text.toString().trim()
        if(TextUtils.isEmpty(emailStr)){
            Toast.makeText(this, "Enter your Email!", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            etForgetEmail.error = "Please enter a valid email address!"
            etForgetEmail.requestFocus()
            return
        }
        loadingDialog.startLoading()
        mAuth.sendPasswordResetEmail(emailStr)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful()){
                    loadingDialog.hideLoading()
                    Toast.makeText(this, "Please check your email and change password!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    loadingDialog.hideLoading()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                    etForgetEmail.requestFocus()
                }

            }
    }
}