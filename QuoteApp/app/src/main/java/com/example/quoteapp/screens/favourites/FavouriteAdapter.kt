package com.example.quoteapp.screens.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteapp.R
import com.example.quoteapp.database.Quote
import com.example.quoteapp.screens.utils.FavouriteViewHolder

class FavouriteAdapter(private val onRemoveButtonClickListener: (quote: Quote) -> Unit) : RecyclerView.Adapter<FavouriteViewHolder>() {
    private var favourites = listOf<Quote>()

    override fun getItemCount() = favourites.size

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val currentFavourite = favourites[position]

        holder.itemView.findViewById<TextView>(R.id.text_view_title).text = currentFavourite.text
        holder.itemView.findViewById<TextView>(R.id.text_view_description).text = currentFavourite.author

        val removeButton = holder.itemView.findViewById<Button>(R.id.removeButton)
        removeButton.setOnClickListener{
            currentFavourite.isFavourite = false
            onRemoveButtonClickListener(currentFavourite)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_item_favourite, parent, false) as CardView

        return FavouriteViewHolder(itemView)
    }

    fun setFavourites(favourites: List<Quote>){
        this.favourites = favourites
        notifyDataSetChanged()
    }

}