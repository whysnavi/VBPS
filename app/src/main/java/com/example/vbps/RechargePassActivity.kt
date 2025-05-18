package com.example.vbps


 import com.example.vbps.R

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class RechargePassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_pass)

        val rechargePassNumber = findViewById<EditText>(R.id.rechargePassNumber)
        val rechargeButton = findViewById<Button>(R.id.rechargeButton)
        val qrCodeImage = findViewById<ImageView>(R.id.qrCodeImage)

        rechargeButton.setOnClickListener { view: View? ->
            val passNumber = rechargePassNumber.text.toString()
            if (passNumber.isEmpty()) {
                Toast.makeText(
                    this@RechargePassActivity,
                    "Please enter Bus Pass Number",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                try {
                    val barcodeEncoder = BarcodeEncoder()
                    val bitmap = barcodeEncoder.encodeBitmap(
                        "Recharge_$passNumber",
                        BarcodeFormat.QR_CODE,
                        400,
                        400
                    )
                    qrCodeImage.setImageBitmap(bitmap)
                    qrCodeImage.visibility = View.VISIBLE
                } catch (e: WriterException) {
                    e.printStackTrace()
                }
            }
        }
    }
}