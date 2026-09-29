package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TotalSavingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_savings)

        FinanceManager.init(this)
        val totalSavings = FinanceManager.getTotalSavings()
        findViewById<TextView>(R.id.tvTotalSavingsHeader).text = "Total Savings: ₱%,.2f".format(totalSavings)

        updateSavingsChart()

        findViewById<Button>(R.id.btnSavingsBack).setOnClickListener {
            finish()
        }

        setupBottomNav()
    }

    private fun updateSavingsChart() {
        val totalSavings = FinanceManager.getTotalSavings()
        // Mock historical data scaled to current total
        val baseHeights = listOf(0.6, 0.8, 0.5, 0.9, 1.0) 
        val barIds = listOf(R.id.barSavings1, R.id.barSavings2, R.id.barSavings3, R.id.barSavings4, R.id.barSavings5)
        
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