package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        findViewById<TextView>(R.id.tvProfileDetailName).text = UserManager.currentUser?.name ?: "User"

        findViewById<Button>(R.id.btnProfileBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnProfileBackHome).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finishAffinity() // Clear task and go home
        }

        findViewById<Button>(R.id.btnProfileLogout).setOnClickListener {
            UserManager.clearLoginState()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}