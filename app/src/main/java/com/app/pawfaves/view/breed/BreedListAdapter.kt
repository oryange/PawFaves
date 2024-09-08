package com.app.pawfaves.view.breed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.pawfaves.R
import com.app.pawfaves.databinding.BreedItemBinding
import com.app.pawfaves.model.data.entities.MessageData
import com.squareup.picasso.Picasso

class BreedListAdapter(
    private var breedList: List<MessageData>,
    val favoriteListener: (newItem: String) -> Unit
) :
    RecyclerView.Adapter<BreedListAdapter.BreedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val itemView = BreedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BreedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breed = breedList[position]
        holder.bind(breed)
    }

    override fun getItemCount(): Int = breedList.size

    fun setList(list: List<MessageData>) {
        if (breedList.isNullOrEmpty()) {
            breedList = list
        }
        notifyDataSetChanged()
    }

    inner class BreedViewHolder(private val itemBinding: BreedItemBinding) :
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
