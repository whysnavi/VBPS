package com.example.vbps

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class NewPassActivity : AppCompatActivity() {

    private lateinit var studentName: EditText
    private lateinit var fatherName: EditText
    private lateinit var dob: EditText
    private lateinit var whatsapp: EditText
    private lateinit var institution: EditText
    private lateinit var admission: EditText
    private lateinit var aadhar: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var paymentButton: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pass)

        // Initialize UI components
        dbHelper = DatabaseHelper(this)

        studentName = findViewById(R.id.studentName)
        fatherName = findViewById(R.id.fatherName)
        dob = findViewById(R.id.dob)
        whatsapp = findViewById(R.id.whatsappNumber)
        institution = findViewById(R.id.institutionName)
        admission = findViewById(R.id.admissionNumber)
        aadhar = findViewById(R.id.aadharNumber)
        genderGroup = findViewById(R.id.genderGroup)
        paymentButton = findViewById(R.id.paymentButton)
        val saveButton = findViewById<Button>(R.id.saveButton)

        val busPassNumberTextView = findViewById<TextView>(R.id.busPassNumberTextView)

        // Save Button Logic
        saveButton.setOnClickListener {
            val name = studentName.text.toString()
            val fName = fatherName.text.toString()
            val dobStr = dob.text.toString()
            val whatsappStr = whatsapp.text.toString()
            val institutionStr = institution.text.toString()
            val admissionStr = admission.text.toString()
            val aadharStr = aadhar.text.toString()
            val selectedGenderId = genderGroup.checkedRadioButtonId
            val selectedGender = findViewById<RadioButton>(selectedGenderId)
            val gender = selectedGender?.text.toString()

            if (name.isEmpty() || fName.isEmpty() || dobStr.isEmpty() || aadharStr.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                // Save user details to the database
                val passId = dbHelper.insertPass(
                    name,
                    fName,
                    gender,
                    dobStr,
                    whatsappStr,
                    institutionStr,
                    admissionStr,
                    aadharStr
                )

                // Generate a unique Bus Pass Number
                val busPassNumber = "BP${System.currentTimeMillis()}"

                // Update the database with the Bus Pass Number
                dbHelper.updatePassNumber(passId, busPassNumber)

                // Display the Bus Pass Number
                busPassNumberTextView.text = "Your Bus Pass Number: $busPassNumber"
                busPassNumberTextView.visibility = View.VISIBLE

                // Show the pass number as a Toast as well
                Toast.makeText(this, "Pass Created! Your Bus Pass Number: $busPassNumber", Toast.LENGTH_LONG).show()

                // Make the Payment button visible for further actions
                paymentButton.visibility = View.VISIBLE
            }
        }

        // Payment Button Logic (QR Code Generation)
        paymentButton.setOnClickListener {
            try {
                val qrCodeContent = "PaymentQRCode: ${System.currentTimeMillis()}"
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(
                    qrCodeContent,
                    BarcodeFormat.QR_CODE,
                    400,
                    400
                )

                // Display QR Code
                val qrCodeImageView = ImageView(this)
                qrCodeImageView.setImageBitmap(bitmap)

                Toast.makeText(this, "QR Code Generated!", Toast.LENGTH_SHORT).show()
                // You could navigate to another screen to show QR code or embed it in the UI
            } catch (e: WriterException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to generate QR Code", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
