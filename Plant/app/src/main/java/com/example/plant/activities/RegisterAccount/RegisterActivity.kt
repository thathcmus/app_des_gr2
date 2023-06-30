package com.example.plant.activities.RegisterAccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.util.Patterns
import com.example.plant.R
import com.example.plant.activities.Login.LoginActivity
import com.example.plant.firestore.Firestore
import com.example.plant.model.User
import com.example.plant.util.ProgressBarLoading
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.btnRegister
import kotlinx.android.synthetic.main.activity_register.etRegisterEmail
import kotlinx.android.synthetic.main.activity_register.etRegisterLocation
import kotlinx.android.synthetic.main.activity_register.etRegisterName
import kotlinx.android.synthetic.main.activity_register.etRegisterPass

class RegisterActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val loadingDialog = ProgressBarLoading(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //hide action bar
        supportActionBar?.hide()
        //get user instance
        mAuth = FirebaseAuth.getInstance()
        // listen register button event
        btnRegister.setOnClickListener() {
            register()
        }
    }
    private fun register() {
        val emailStr = etRegisterEmail.text.toString().trim()
        val passStr = etRegisterPass.text.toString().trim()
        val nameStr = etRegisterName.text.toString().trim()
        val locaStr = etRegisterLocation.text.toString().trim()
        //check fields is empty
        if(TextUtils.isEmpty(emailStr)){
            Toast.makeText(this, "Enter your email!", Toast.LENGTH_SHORT).show()
            return
        }else if(TextUtils.isEmpty(passStr)){
            Toast.makeText(this, "Enter your password!", Toast.LENGTH_SHORT).show()
            return
        }else if(TextUtils.isEmpty(nameStr)){
            Toast.makeText(this, "Enter your Name!", Toast.LENGTH_SHORT).show()
            return
        }else if(TextUtils.isEmpty(locaStr)){
            Toast.makeText(this, "Enter your Address!", Toast.LENGTH_SHORT).show()
            return
        }
        //check email has true format
        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            etRegisterEmail.error = "Please enter a valid email address!"
            etRegisterEmail.requestFocus()
            return
        }

        //check conditional password
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{11,}\$".toRegex()
        if (!passwordRegex.matches(passStr)) {
            etRegisterPass.error =
                "Password with 11 characters or more, including a combination of letters, numbers and special characters @, $, !, %, *, ?, &"
            etRegisterPass.requestFocus()
            return
        }
        //start Loading Dialog
        loadingDialog.startLoading()

        //create account on firebase
        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful()){
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val userInfo = User(
                        firebaseUser.uid,nameStr,emailStr, 0 ,"", locaStr, ""
                    )
                    // store User Info into Firestore
                    Firestore().registerUser(this,userInfo)
                    //imform register success
                    registerSuccess()
                }
                else
                {
                    // inform create account failed
                    loadingDialog.hideLoading()
                    Toast.makeText(this, "Create account failed!", Toast.LENGTH_SHORT).show()
                }

            }
    }
   private fun registerSuccess () {
        loadingDialog.hideLoading()
        Toast.makeText(this, "Create account successed!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}