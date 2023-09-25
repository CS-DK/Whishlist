package com.example.whishlist

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    private val items = mutableListOf<WishlistItem>()

    // ViewHolder class holds references to the item's views
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView
        val priceTextView: TextView
        val urlTextView: TextView

        init {
            nameTextView = itemView.findViewById(R.id.itemNameTv)
            priceTextView = itemView.findViewById(R.id.itemPriceTv)
            urlTextView = itemView.findViewById(R.id.itemSiteTv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.priceTextView.text = "$${item.price}"
        holder.urlTextView.text = item.url

        holder.itemView.setOnLongClickListener {
            // Prompt the user for confirmation to delete the item
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
            alertDialogBuilder.setTitle("Delete Item")
            alertDialogBuilder.setMessage("Are you sure you want to delete this item?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                // Remove the item from the dataset
                items.removeAt(position)
                // Notify the adapter of the item removal
                notifyItemRemoved(position)
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.show()

            true // Consume the long-click event
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: WishlistItem) {
        items.add(item)
        notifyDataSetChanged() // Notify the adapter that the data set has changed
        Log.d("WishlistAdapter", "Item added: ${item.name}")
    }
}
