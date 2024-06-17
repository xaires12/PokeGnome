package com.example.pokegnomego.models

data class UserAchievement(
    val name: String,
    val gnome_count: Int
)

data class UserAchievements(
    val achievements: List<UserAchievement>
)