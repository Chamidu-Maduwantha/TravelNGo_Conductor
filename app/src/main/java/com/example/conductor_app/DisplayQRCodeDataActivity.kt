package com.example.conductor_app

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


class DisplayQRCodeDataActivity : AppCompatActivity() {



    private lateinit var profileImage: ImageView
    private lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_qrcode_data)

        val qrCodeResult = intent.getStringExtra("qrCodeResult")

        /*val resultTextView: TextView = findViewById(R.id.textViewUsername)
        resultTextView.text = qrCodeResult*/



        val searchText = qrCodeResult

        if (searchText != null) {
            findUserByText(searchText)
        }

    }

    private fun findUserByText(searchText: String) {
        val usersRef = FirebaseDatabase.getInstance().getReference("passenger")
        val query = usersRef.orderByChild("userId").equalTo(searchText)

        FirebaseApp.initializeApp(this)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(Passenger::class.java)


                        val currentDate = Date()
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val todayDate = dateFormat.format(currentDate)



                        //val traveledDateRef = FirebaseDatabase.getInstance().getReference("passenger").child(uid).child("Travel date")

                        val name = user?.username
                        val NIC = user?.NIC
                        val From= user?.from
                        val To = user?.to
                        val type = user?.passType
                        val last = user?.LastTraveled

                        val naem: TextView = findViewById(R.id.name)
                        val nic : TextView = findViewById(R.id.nic)
                        val dat: TextView = findViewById(R.id.date)
                        val btnmark : Button = findViewById(R.id.mark)
                        val btnback : LinearLayout = findViewById(R.id.linscan)
                        val medium : TextView= findViewById(R.id.medium)
                        val to :TextView= findViewById(R.id.to)
                        val from:TextView =findViewById(R.id.from)



                        naem.text = name
                        nic.text = NIC
                        dat.text = todayDate
                        medium.text= type
                        to.text = To
                        from.text =From




                        btnmark.setOnClickListener {

                            val datesRef = userSnapshot.child("Travel date")
                            val datesList = mutableListOf<String>()
                            datesRef.children.forEach { dateSnapshot ->
                                val date = dateSnapshot.getValue(String::class.java)
                                date?.let { datesList.add(it) }
                            }
                            datesList.add(todayDate)
                            val updatedDatesRef = userSnapshot.ref.child("Travel date")
                            updatedDatesRef.removeValue()  // Remove the existing "Travel date" field
                           datesList.forEach { date ->
                                updatedDatesRef.push().setValue(date)  // Use push() to generate unique keys
                            }
                            /*val newDataRef = traveledDateRef.push()
                            newDataRef.setValue("$currentDate")*/


                            val lastTraveledDateRef = userSnapshot.ref.child("Travel date").child("LastTraveled")
                            lastTraveledDateRef.setValue(todayDate)

                            btnmark.setBackgroundColor(resources.getColor(R.color.button_clicked_color))
                            btnmark.text="Scan another QR"


                            btnmark.setOnClickListener {
                                val intent = Intent(this@DisplayQRCodeDataActivity,ScannerActivity::class.java)
                                startActivity(intent)

                            }








                        }


                        btnback.setOnClickListener {
                            val intent = Intent(this@DisplayQRCodeDataActivity,ScannerActivity::class.java)
                            startActivity(intent)
                        }





                    }
                } else {
                    println("No users found with the provided text.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Database error occurred: ${databaseError.message}")
            }
        })
    }



}