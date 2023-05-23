package com.example.conductor_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    lateinit var btnsign: Button
    lateinit var rname: EditText
    lateinit var rpassword: EditText
    lateinit var remail: EditText
    lateinit var cpassword: EditText
    lateinit var nic : EditText
    lateinit var birthday : EditText
    lateinit var address : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        btnsign = findViewById(R.id.btnsign)
        rname = findViewById(R.id.edtname)
        rpassword = findViewById(R.id.r_password)
        remail = findViewById(R.id.r_email)
        cpassword = findViewById(R.id.r_cpassword)
        nic = findViewById(R.id.nic)

        btnsign.setOnClickListener {
            val userName = rname.text.toString()
            val email = remail.text.toString()
            val password = rpassword.text.toString()
            val comfirmpassword = cpassword.text.toString()
            val nic = nic.text.toString()



            if(TextUtils.isEmpty(userName)){
                Toast.makeText(applicationContext,"username is required", Toast.LENGTH_SHORT).show()
            }

            if(TextUtils.isEmpty(nic)){
                Toast.makeText(applicationContext,"NIC is required", Toast.LENGTH_SHORT).show()
            }

            if(TextUtils.isEmpty(email)){
                Toast.makeText(applicationContext,"email Required", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(password)){
                Toast.makeText(applicationContext,"password required", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(comfirmpassword)){
                Toast.makeText(applicationContext,"confirm password required", Toast.LENGTH_SHORT).show()
            }

            if (password != comfirmpassword){
                Toast.makeText(applicationContext,"password not matched", Toast.LENGTH_SHORT).show()

            }


            registerUser(userName, email, password,nic)

        }


    }


    private fun registerUser(userName:String, email:String, password:String, nic:String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {
            if(it.isSuccessful){
                var user: FirebaseUser? = auth.currentUser
                var userId:String = user!!.uid
                databaseReference = FirebaseDatabase.getInstance().getReference("conductor").child(userId)

                var hashMap:HashMap<String,String> = HashMap()
                hashMap.put("userId",userId)
                hashMap.put("username",userName)
                hashMap.put("nic",nic)


                databaseReference.setValue(hashMap).addOnCompleteListener (this){
                    if (it.isSuccessful){
                        var intent = Intent(this@Register,ScannerActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }


}