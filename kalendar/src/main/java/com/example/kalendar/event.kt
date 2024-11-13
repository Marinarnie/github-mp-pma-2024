package com.example.kalendar

data class event (
    val title: String,
    val date: String, // Můžete použít Date nebo LocalDate, pokud chcete
    val category: String // Např. "osobní", "pracovní", atd.
)