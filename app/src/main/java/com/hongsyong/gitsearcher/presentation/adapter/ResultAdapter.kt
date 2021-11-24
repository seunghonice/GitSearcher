package com.hongsyong.gitsearcher.presentation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hongsyong.gitsearcher.R
import com.hongsyong.gitsearcher.data.model.Repository

class ResultAdapter(var results: List<Repository>?, private val context: Context) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView
        var ownerName: TextView
        var repositoryName: TextView
        var score: TextView
        var repositoryUrl = ""

        init {
            itemView.run {
                avatar = findViewById(R.id.iv_avatar)
                ownerName = findViewById(R.id.tv_owner_name)
                repositoryName = findViewById(R.id.tv_repo_name)
                score = findViewById(R.id.tv_score)
            }
            itemView.setOnClickListener {
                try {
                    val uri = Uri.parse(repositoryUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(context, intent, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (results == null) return

        val item = results!![position]
        holder.run {
            Glide.with(context)
                .load(item.owner.avatarUrl)
                .placeholder(R.drawable.avatar_background)
                .dontAnimate()
                .into(avatar)
            avatar.clipToOutline = true
            ownerName.text = item.owner.login
            repositoryName.text = item.name
            repositoryUrl = item.url
            score.text = item.score.toString()
        }
    }

    override fun getItemCount(): Int = results?.size ?: 0
}