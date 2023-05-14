package com.example.conductor_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        val scanButton = findViewById<Button>(R.id.scanButton)
        scanButton.setOnClickListener {

            scanQRCode()
        }
    }

    private fun scanQRCode() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Log.d("ScannerActivity", "Scan cancelled")
            } else {
                val qrCodeResult = result.contents
                val intent = Intent(this, DisplayQRCodeDataActivity::class.java)
                intent.putExtra("qrCodeResult", qrCodeResult)
                startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}