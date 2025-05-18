package com.example.vbps

import android.database.Cursor
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ViewPassActivity : AppCompatActivity() {

    private lateinit var busPassNumberInput: EditText
    private lateinit var fetchPassButton: Button
    private lateinit var passDetailsTextView: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pass)

        // Initialize UI components
        busPassNumberInput = findViewById(R.id.busPassNumberInput)
        fetchPassButton = findViewById(R.id.fetchPassButton)
        passDetailsTextView = findViewById(R.id.passDetailsTextView)
        dbHelper = DatabaseHelper(this)

        // Fetch Pass Details Button Logic
        fetchPassButton.setOnClickListener {
            val busPassNumber = busPassNumberInput.text.toString().trim()

            if (busPassNumber.isEmpty()) {
                Toast.makeText(this, "Please enter a Bus Pass Number", Toast.LENGTH_SHORT).show()
            } else {
                fetchPassDetails(busPassNumber)
            }
        }
    }

    private fun fetchPassDetails(busPassNumber: String) {
        // Query the database for the pass details
        val cursor: Cursor? = dbHelper.getPassByNumber(busPassNumber)

        if (cursor != null && cursor.moveToFirst()) {
            try {
                // Extract pass details safely
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val fatherName = cursor.getString(cursor.getColumnIndexOrThrow("father_name"))
                val gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"))
                val dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"))
                val whatsapp = cursor.getString(cursor.getColumnIndexOrThrow("whatsapp"))
                val institution = cursor.getString(cursor.getColumnIndexOrThrow("institution"))
                val admission = cursor.getString(cursor.getColumnIndexOrThrow("admission"))
                val aadhar = cursor.getString(cursor.getColumnIndexOrThrow("aadhar"))
                val busPassNumberFromDB = cursor.getString(cursor.getColumnIndexOrThrow("bus_pass_number"))

                // Format the details for display
                val passDetails = """
                    Bus Pass Number: $busPassNumberFromDB
                    Name: $name
                    Father's Name: $fatherName
                    Gender: $gender
                    DOB: $dob
                    WhatsApp: $whatsapp
                    Institution: $institution
                    Admission Number: $admission
                    Aadhar: $aadhar
                """.trimIndent()

                // Display the details
                passDetailsTextView.text = passDetails
                passDetailsTextView.visibility = TextView.VISIBLE
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, "Error fetching column data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle case where no details are found
            passDetailsTextView.text = ""
            passDetailsTextView.visibility = TextView.GONE
            Toast.makeText(this, "No pass found with the given number", Toast.LENGTH_SHORT).show()
        }

        cursor?.close()
    }
}
