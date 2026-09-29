package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cashguard.R

class ReportsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        FinanceManager.init(this)
        TransactionManager.init(this)
        val currentUser = UserManager.currentUser?.username ?: "demo"

        // Dynamic Data
        val totalSavings = FinanceManager.getTotalSavings()
        val totalSpending = FinanceManager.getMonthlySpending(currentUser)

        findViewById<android.widget.TextView>(R.id.tvTotalSavingsValue).text = "₱%,.2f".format(totalSavings)
        findViewById<android.widget.TextView>(R.id.tvTotalSpendingValue).text = "₱%,.2f".format(totalSpending)

        updateYearlyChart()

        findViewById<android.view.View>(R.id.cardTotalSavings).setOnClickListener {
            startActivity(Intent(this, TotalSavingsActivity::class.java))
        }

        findViewById<android.view.View>(R.id.cardTotalSpending).setOnClickListener {
            startActivity(Intent(this, TotalSpendingActivity::class.java))
        }

        setupBottomNav()
    }

    private fun updateYearlyChart() {
        // Mock data for yearly analysis (Jan to May)
        val monthlySavings = listOf(100.0, 150.0, 120.0, 200.0, 180.0) // Heights in dp
        val barIds = listOf(R.id.barJan, R.id.barFeb, R.id.barMar, R.id.barApr, R.id.barMay)
        
        for (i in barIds.indices) {
            val bar = findViewById<android.view.View>(barIds[i])
            val params = bar.layoutParams
            params.height = (monthlySavings[i] * resources.displayMetrics.density).toInt()
            bar.layoutParams = params
        }
    }

    private fun setupBottomNav() {
        findViewById<Button>(R.id.btnNavHome).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnNavReports).setTextColor(getColor(R.color.white))
        
        findViewById<Button>(R.id.btnNavTransactions).setOnClickListener {
            startActivity(Intent(this, TransactionsActivity::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnNavHistory).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnNavSettings).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}