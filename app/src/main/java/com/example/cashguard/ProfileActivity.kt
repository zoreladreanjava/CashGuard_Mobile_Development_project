package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cashguard.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserManager.init(this)
        setContentView(R.layout.activity_profile)

        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)
        val btnOpenLogs = findViewById<Button>(R.id.btnOpenLogs)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnProfile = findViewById<Button>(R.id.btnProfile)

        btnChangePassword.setOnClickListener {
            showChangePasswordDialog()
        }

        btnOpenLogs.setOnClickListener {
            showLogsDialog()
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }

        btnLogout.setOnClickListener {
            UserManager.clearLoginState()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        findViewById<Button>(R.id.btnNavHome).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavReports).setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavTransactions).setOnClickListener {
            startActivity(Intent(this, TransactionsActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavHistory).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavSettings).setTextColor(getColor(R.color.white))
    }

    private fun showChangePasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_password, null)
        val etCurrentPassword = dialogView.findViewById<EditText>(R.id.etCurrentPassword)
        val etNewPassword = dialogView.findViewById<EditText>(R.id.etNewPassword)
        val etConfirmPassword = dialogView.findViewById<EditText>(R.id.etConfirmPassword)

        AlertDialog.Builder(this)
            .setTitle("Change Password")
            .setView(dialogView)
            .setPositiveButton("Change") { _, _ ->
                val currentPass = etCurrentPassword.text.toString()
                val newPass = etNewPassword.text.toString()
                val confirmPass = etConfirmPassword.text.toString()
                val currentUser = UserManager.currentUser

                if (currentPass != currentUser?.password) {
                    Toast.makeText(this, "❌ Current password is incorrect", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (newPass.isEmpty()) {
                    Toast.makeText(this, "❌ New password cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (newPass != confirmPass) {
                    Toast.makeText(this, "❌ New passwords do not match", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val updatedUser = currentUser?.copy(password = newPass)
                if (updatedUser != null) {
                    UserManager.updateUser(updatedUser)
                    Toast.makeText(this, "✅ Password changed successfully!", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showLogsDialog() {
        val logs = getAppLogs()
        val dialogView = layoutInflater.inflate(R.layout.dialog_logs, null)
        val tvLogs = dialogView.findViewById<android.widget.TextView>(R.id.tvLogs)
        tvLogs.text = logs

        AlertDialog.Builder(this)
            .setTitle("Application Logs")
            .setView(dialogView)
            .setPositiveButton("Close", null)
            .setNeutralButton("Clear Logs") { _, _ ->
                clearLogs()
                Toast.makeText(this, "Logs cleared", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun getAppLogs(): String {
        val logFile = File(filesDir, "app_logs.txt")
        return if (logFile.exists()) {
            logFile.readText()
        } else {
            "No logs found.\nLogin to start logging activities."
        }
    }

    private fun clearLogs() {
        val logFile = File(filesDir, "app_logs.txt")
        if (logFile.exists()) {
            logFile.writeText("")
        }
    }

    override fun onResume() {
        super.onResume()
        UserManager.currentUser?.let {
            writeLog("User ${it.username} accessed Settings")
        }
    }

    private fun writeLog(message: String) {
        val logFile = File(filesDir, "app_logs.txt")
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val logEntry = "[$timestamp] $message\n"
        logFile.appendText(logEntry)
    }
}