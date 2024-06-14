package com.example.pokegnomego.models

data class Visit(
    val visitId: Int,
    val userId: Int,
    val gnomeId: Int,
    val photo: String?,
    val visitDate: String,
    val latitude: Double,
    val longitude: Double
)
