package com.example.conductor_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.CheckBox
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
    lateinit var checkbox : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        btnsign = findViewById(R.id.btnsign)
        rpassword = findViewById(R.id.r_password)
        remail = findViewById(R.id.r_email)
        cpassword = findViewById(R.id.r_cpassword)
        nic = findViewById(R.id.nic)
        checkbox = findViewById(R.id.chkbox)

        btnsign.setOnClickListener {

            val email = remail.text.toString()
            val password = rpassword.text.toString()
            val comfirmpassword = cpassword.text.toString()
            val nic = nic.text.toString()





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

            if (!checkbox.isChecked){
                Toast.makeText(applicationContext,"You should agree to Terms and Conditions",Toast.LENGTH_SHORT).show()
            }else{
                registerUser(email, password,nic)
            }


        }


    }


    private fun registerUser(email:String, password:String, nic:String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {
            if(it.isSuccessful){
                var user: FirebaseUser? = auth.currentUser
                var userId:String = user!!.uid
                databaseReference = FirebaseDatabase.getInstance().getReference("conductor").child(userId)

                var hashMap:HashMap<String,String> = HashMap()
                hashMap.put("userId",userId)
                hashMap.put("nic",nic)
                hashMap.put("account","conductor")


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