package com.app.pawfaves.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.pawfaves.R
import com.app.pawfaves.databinding.BreedItemBinding
import com.app.pawfaves.model.data.entities.MessageData

import com.squareup.picasso.Picasso

class FavoriteAdapter(
    private var breedList: List<MessageData>,
    val favoriteListener: (newItem: String) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val itemView = BreedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val breed = breedList[position]
        holder.bind(breed)
    }

    override fun getItemCount(): Int = breedList.size

    fun setList(list: List<MessageData>) {
        breedList = list
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val itemBinding: BreedItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(breed: MessageData) {
            val item = itemBinding.dogItem
            val favorite = itemBinding.itemFavorite

            if (breed.isFavorite) favorite.setImageResource(R.drawable.ic_favorite_selected)
            else favorite.setImageResource(R.drawable.ic_favorite_empty)
            favorite.setOnClickListener { favoriteListener(breed.message) }
            Picasso.get().load(breed.message).into(item)
        }
    }
}
