package com.example.phauthapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {
    private lateinit var followerPage:Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var logoutBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        mAuth= FirebaseAuth.getInstance()
        logoutBtn=findViewById(R.id.logOut)
        logoutBtn.setOnClickListener {
          mAuth.signOut()
            val loginActivity=Intent(this,MainActivity::class.java)
            startActivity(loginActivity)
            finish()
        }
        followerPage=findViewById(R.id.followpage)
        followerPage.setOnClickListener {
            val openInsta=Intent(Intent.ACTION_VIEW)
            openInsta.setData(Uri.parse("https://www.instagram.com/androcoding/"))
            startActivity(openInsta)
        }

        }
    }
