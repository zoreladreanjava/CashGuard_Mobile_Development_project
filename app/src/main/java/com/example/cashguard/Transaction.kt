package com.example.cashguard

data class Transaction(
    val category: String,
    val description: String,
    val amount: String,
    val timestamp: Long
) {
    val formattedDate: String
        get() {
            val date = java.util.Date(timestamp)
            return java.text.SimpleDateFormat("MMM dd, yyyy HH:mm", java.util.Locale.getDefault()).format(date)
        }
}