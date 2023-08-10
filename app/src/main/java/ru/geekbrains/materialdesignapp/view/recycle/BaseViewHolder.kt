package ru.geekbrains.materialdesignapp.view.recycle

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.materialdesignapp.model.recycler.Data

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Data)
}