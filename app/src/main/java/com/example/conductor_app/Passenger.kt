package com.example.conductor_app

import java.util.Date

data class Passenger(var username :String?=null, var NIC : String?=null,var from:String?=null, var to: String?=null, var passType:String?=null,
                     var  LastTraveled : Date?=null
)
