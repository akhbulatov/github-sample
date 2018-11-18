package com.akhbulatov.githubsample.ui.main.favoritesroot.favorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akhbulatov.githubsample.R
import com.akhbulatov.githubsample.models.UserDetails
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_favorite.*

class FavoritesAdapter(
    private val favorites: List<UserDetails>
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(favorites[position])

    override fun getItemCount(): Int = favorites.size

    class FavoriteViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(favorites: UserDetails) {
            Picasso.get()
                .load(favorites.avatarUrl)
                .placeholder(R.drawable.ic_account_circle_black)
                .into(avatarImageView)
            loginTextView.text = favorites.login
            nameTextView.text = favorites.name
        }
    }
}