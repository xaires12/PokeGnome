package com.example.pokegnomego.models

import java.sql.Blob

data class Gnome(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val photo: Blob
)
