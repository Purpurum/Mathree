package com.purp.mathree.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.purp.mathree.R
import com.purp.mathree.databinding.ItemTileBinding
import com.purp.mathree.model.Tile

class TileAdapter : RecyclerView.Adapter<TileAdapter.TileViewHolder>() {
    private val tiles: MutableList<Tile> = mutableListOf()
    private var onTileClickListener: ((Tile) -> Unit)? = null

    fun setTiles(newTiles: List<Tile>) {
        tiles.clear()
        tiles.addAll(newTiles)
        notifyDataSetChanged()
    }

    fun setOnTileClickListener(listener: (Tile) -> Unit) {
        onTileClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tile, parent, false)
        return TileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        val tile = tiles[position]
        holder.bind(tile)
    }

    override fun getItemCount(): Int {
        return tiles.size
    }

    inner class TileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.tileImageView)

        fun bind(tile: Tile) {
            imageView.setImageResource(tile.drawable)

            itemView.setOnClickListener {
                onTileClickListener?.invoke(tile)
            }
        }
    }
}