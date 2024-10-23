package com.example.laba2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import Miniature

class MiniatureAdapter(
    private val dataList: MutableList<Miniature>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<MiniatureAdapter.ViewHolder>() {

    // Интерфейс для обработки кликов и удаления
    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
        val itemFaction: TextView = itemView.findViewById(R.id.itemFaction)
        val miniatureImage: ImageView = itemView.findViewById(R.id.miniatureImage)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
            deleteIcon.setOnClickListener {
                clickListener.onDeleteClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val miniature = dataList[position]
        holder.itemTitle.text = miniature.name
        holder.itemFaction.text = miniature.faction

        if (miniature.imageUrls.isNotEmpty()) {
            // Загрузка первого изображения из списка
            Glide.with(holder.itemView.context)
                .load(miniature.imageUrls[0])
                .into(holder.miniatureImage)
        }
    }



    override fun getItemCount(): Int = dataList.size

    // Функция для удаления элемента
    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

}
