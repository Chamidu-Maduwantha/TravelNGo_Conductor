package com.example.conductor_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var lemail: EditText
    lateinit var lpassword: EditText
    lateinit var btnlog : Button
    private lateinit var auth: FirebaseAuth
    lateinit var reg : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = FirebaseAuth.getInstance()

        btnlog = findViewById(R.id.btnlog)
        lemail = findViewById(R.id.elog_email)
        lpassword = findViewById(R.id.elog_password)
        reg =  findViewById(R.id.reg)

        reg.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        btnlog.setOnClickListener {
            val email = lemail.text.toString()
            val password = lpassword.text.toString()

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                Toast.makeText(
                    applicationContext,
                    "email and password required",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    if (it.isSuccessful) {

                        /*val sessionManager = SessionManager(this)
                        sessionManager.setLoggedIn(true)*/

                        val intent = Intent(this@Login, ScannerActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "email or password incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }


        }
    }
}