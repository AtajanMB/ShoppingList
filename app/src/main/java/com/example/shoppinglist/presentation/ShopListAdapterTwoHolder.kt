package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapterTwoHolder : ListAdapter<ShopItem, RecyclerView.ViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val layout = when (viewType) {
//            ENABLED -> R.layout.item_shop_enabled
//            DISABLED -> R.layout.item_shop_disabled
//            else -> throw (RuntimeException("unknown view type: $viewType"))
//        }

        return when (viewType) {
            ENABLED -> ShopListViewHolderWithEnabled(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_enabled, parent, false))

            DISABLED -> ShopListViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_disabled, parent, false))

            else -> throw (RuntimeException("unknown view type: $viewType"))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shopItem = getItem(position)

        when (holder) {
            is ShopListViewHolder -> {
                holder.tvName.text = shopItem.name
                holder.tvCount.text = shopItem.count.toString()
            }

            is ShopListViewHolderWithEnabled -> {
                holder.tvName.text = shopItem.name
                holder.tvCount.text = shopItem.count.toString()
                holder.enabled.text = shopItem.enabled.toString()

            }
        }


        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            ENABLED
        } else {
            DISABLED
        }
    }


    companion object {
        const val ENABLED = 100
        const val DISABLED = 101
        const val MAX_POOL_SIZE = 15
    }
}