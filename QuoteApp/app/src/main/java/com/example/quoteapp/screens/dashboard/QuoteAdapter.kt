package com.example.quoteapp.screens.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteapp.R
import com.example.quoteapp.database.Quote
import com.example.quoteapp.screens.utils.QuoteViewHolder

class QuoteAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<QuoteViewHolder>() {
    private var quotes = listOf<Quote>()

    override fun getItemCount() = quotes.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentQuote = quotes[position]

        holder.itemView.findViewById<TextView>(R.id.text_view_title).text = currentQuote.text
        holder.itemView.findViewById<TextView>(R.id.text_view_description).text = currentQuote.author

        // Bind favourited state
        val favouriteCheckBox = holder.itemView.findViewById<CheckBox>(R.id.favouriteCheckbox)
        favouriteCheckBox.isChecked = currentQuote.isFavourite

        // Handle favorite checkbox click
        favouriteCheckBox.setOnClickListener {
            val updatedQuote = currentQuote.copy(isFavourite = !currentQuote.isFavourite)
            onClickListener.onFavouriteClick(updatedQuote)
        }
        // Handle detailView click
        holder.itemView.findViewById<LinearLayout>(R.id.text_view_wrapper).setOnClickListener{
            onClickListener.onCardViewClick(currentQuote)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_item, parent, false) as CardView

        return QuoteViewHolder(itemView)
    }

    fun setQuotes(quotes: List<Quote>) {
        this.quotes = quotes
        notifyDataSetChanged()
    }

    class OnClickListener(val clickListener: (quote: Quote, toDetailsFragment: Boolean) -> Unit){
        fun onCardViewClick(quote: Quote) {
            clickListener(quote, false)
        }
        fun onFavouriteClick(quote: Quote){
            clickListener(quote, true)
        }
    }
}




