package com.example.phauthapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var auth: FirebaseAuth
    private lateinit var countryCode:EditText
    private lateinit var phoneNumber:EditText
    private lateinit var mSendOtp: Button
    lateinit var storedVerificationId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countryCode=findViewById(R.id.countryCodeText)
        phoneNumber=findViewById(R.id.phone)
        mSendOtp = findViewById(R.id.verifiedBtn)
        auth = FirebaseAuth.getInstance()
        mSendOtp.setOnClickListener {
            login()
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, HomePage::class.java))
                finish()
                Toast.makeText(this@MainActivity, "Successful", Toast.LENGTH_LONG).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@MainActivity, "${p0.message}", Toast.LENGTH_LONG).show()

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                Toast.makeText(this@MainActivity, p0, Toast.LENGTH_LONG).show()
                storedVerificationId = p0
                val otpIntent = Intent(applicationContext, OtpActivity::class.java)
                startActivity(otpIntent)
                otpIntent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(otpIntent)
                finish()
            }
        }
    }

    private fun login() {
        val conCode=countryCode.text.toString()
        val phone=phoneNumber.text.toString()
        val phNo= "+$conCode$phone"
        if (conCode.isNotEmpty()||phone.isNotEmpty()) {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        } else {
            Toast.makeText(this, "Enter  Country code & mobile number", Toast.LENGTH_SHORT).show()
        }
    }
}




