package com.example.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegnomego.R
import com.example.pokegnomego.models.*

class AchievementAdapter(private val achievements: List<UserAchievement>) :
RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = achievements[position]
        holder.achievementName.text = achievement.name
        holder.gnomeCount.text = "Gnome count: ${achievement.gnome_count}"
    }

    override fun getItemCount(): Int {
        return achievements.size
    }

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val achievementName: TextView = itemView.findViewById(R.id.achievementName)
        val gnomeCount: TextView = itemView.findViewById(R.id.gnomeCount)
    }
}

