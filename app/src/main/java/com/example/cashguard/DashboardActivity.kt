package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cashguard.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserManager.init(this)
        FinanceManager.init(this)
        TransactionManager.init(this)
        setContentView(R.layout.activity_dashboard)

        updateGraphs()
        setupBottomNav()
    }

    private fun updateGraphs() {
        val currentUser = UserManager.currentUser?.username ?: "demo"
        val totalSavings = FinanceManager.getTotalSavings()
        val savingsGoal = FinanceManager.getSavingsGoal()
        val totalSpending = FinanceManager.getMonthlySpending(currentUser)
        
        // Savings Goal Calculation
        val goalPercentage = ((totalSavings / savingsGoal) * 100).toInt().coerceIn(0, 100)
        findViewById<android.widget.TextView>(R.id.tvGoalProgress).text = "$goalPercentage% of your ₱${"%,.0f".format(savingsGoal)} goal"
        findViewById<android.widget.ProgressBar>(R.id.pbGoalGraph).progress = goalPercentage

        // Spending Calculation (Assuming a 50k monthly limit for demo)
        val spendingLimit = 50000.0
        val spendingPercentage = ((totalSpending / spendingLimit) * 100).toInt().coerceIn(0, 100)
        findViewById<android.widget.TextView>(R.id.tvSpendingProgress).text = "Used ₱${"%,.2f".format(totalSpending)} of ₱50k budget"
        findViewById<android.widget.ProgressBar>(R.id.pbSpendingGraph).progress = spendingPercentage
    }

    private fun setupBottomNav() {
        findViewById<Button>(R.id.btnNavHome).setTextColor(getColor(R.color.white))

        findViewById<Button>(R.id.btnNavReports).setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavTransactions).setOnClickListener {
            startActivity(Intent(this, TransactionsActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavHistory).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavSettings).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        UserManager.currentUser?.let {
            writeLog("User ${it.username} accessed Dashboard")
        }
    }

    private fun writeLog(message: String) {
        val logFile = File(filesDir, "app_logs.txt")
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val logEntry = "[$timestamp] $message\n"
        logFile.appendText(logEntry)
    }
}