package com.example.pokegnomego

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegnomego.models.RankingUser

class RankingAdapter : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    private var rankingList: List<RankingUser> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val user = rankingList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return rankingList.size
    }

    fun setRankingList(list: List<RankingUser>) {
        rankingList = list
        notifyDataSetChanged()
    }

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.userName)
        private val userScore: TextView = itemView.findViewById(R.id.userScore)

        fun bind(user: RankingUser) {
            userName.text = user.login
            userScore.text = user.visitcount.toString()
        }
    }
}
