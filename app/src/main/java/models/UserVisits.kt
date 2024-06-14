package com.example.pokegnomego.models

data class UserVisit(
    val visitId: Int,
    val gnomeId: Int,
    val photo: String?,
    val visitDate: String,
    val latitude: Double,
    val longitude: Double
)

data class UserVisits(
    val userId: Int,
    val visits: List<UserVisit>,
    val visitCount: Int
)
