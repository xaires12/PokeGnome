package com.example.pokegnomego.models

data class UserAchievement(
    val levelId: Int,
    val name: String,
    val gnomeCount: Int,
    val timeMax: Int
)

data class UserAchievements(
    val userId: Int,
    val userLogin: String,
    val achievements: List<UserAchievement>
)
