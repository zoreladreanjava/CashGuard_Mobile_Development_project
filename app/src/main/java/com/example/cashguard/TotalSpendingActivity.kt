package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TotalSpendingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_spending)

        FinanceManager.init(this)
        TransactionManager.init(this)
        val currentUser = UserManager.currentUser?.username ?: "demo"
        val totalSpending = FinanceManager.getMonthlySpending(currentUser)
        
        findViewById<TextView>(R.id.tvTotalSpendingHeader).text = "Total Spending: ₱%,.2f".format(totalSpending)
        findViewById<TextView>(R.id.tvMonthlySpendingValue).text = "₱%,.2f".format(totalSpending)

        updateSpendingChart()

        findViewById<Button>(R.id.btnSpendingBack).setOnClickListener {
            finish()
        }

        setupBottomNav()
    }

    private fun updateSpendingChart() {
        val currentUser = UserManager.currentUser?.username ?: "demo"
        val totalSpending = FinanceManager.getMonthlySpending(currentUser)
        
        // Mock data scaled to total spending
        val baseHeights = listOf(0.4, 0.9, 0.6, 1.0, 0.7)
        val barIds = listOf(R.id.barSpending1, R.id.barSpending2, R.id.barSpending3, R.id.barSpending4, R.id.barSpending5)
        
        val maxPixels = 160 // max height in dp
        
        for (i in barIds.indices) {
            val bar = findViewById<android.view.View>(barIds[i])
            val params = bar.layoutParams
            params.height = ((baseHeights[i] * maxPixels) * resources.displayMetrics.density).toInt()
            bar.layoutParams = params
        }
    }

    private fun setupBottomNav() {
        findViewById<Button>(R.id.btnNavHome).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnNavReports).setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
            finish()
        }
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