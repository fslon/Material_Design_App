package ru.geekbrains.materialdesignapp.view.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.model.recycler.Data
import ru.geekbrains.materialdesignapp.model.recycler.Data.Companion.TYPE_EARTH
import ru.geekbrains.materialdesignapp.model.recycler.Data.Companion.TYPE_MARS

class RecycleFragmentAdapter(
    private var onListItemClickListener: RecycleViewFragment.OnListItemClickListener,
    private var data: MutableList<Data>
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EARTH -> EarthViewHolder(
                inflater.inflate(R.layout.fragment_recycler_item_earth, parent, false)
                        as View
            )
            TYPE_MARS ->
                MarsViewHolder(
                    inflater.inflate(
                        R.layout.fragment_recycler_item_mars, parent,
                        false
                    ) as View
                )
            else -> HeaderViewHolder(
                inflater.inflate(
                    R.layout.fragment_recycler_item_header, parent,
                    false
                ) as View
            )
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Data(TYPE_MARS, "Mars", "")

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.descriptionTextView).text =
                    data.someDescription
                itemView.findViewById<ImageView>(R.id.wikiImageView).setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            itemView.findViewById<ImageView>(R.id.marsImageView).setOnClickListener {
                onListItemClickListener.onItemClick(data)
            }
            itemView.findViewById<ImageView>(R.id.addItemImageView).setOnClickListener {
                addItem()
            }
            itemView.findViewById<ImageView>(R.id.removeItemImageView).setOnClickListener { removeItem() }
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }


    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

}