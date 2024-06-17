package com.example.pokegnomego.models

data class Visit(
    val user_id: Int,
    val gnome_id: Int,
    val visit_date: String,
    val latitude: Double,
    val longitude: Double
)
