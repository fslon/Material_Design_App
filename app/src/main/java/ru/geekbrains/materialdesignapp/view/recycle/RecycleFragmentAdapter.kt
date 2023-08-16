package ru.geekbrains.materialdesignapp.view.recycle

import android.graphics.Color
import android.view.*
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.model.recycler.Data
import ru.geekbrains.materialdesignapp.model.recycler.Data.Companion.TYPE_EARTH
import ru.geekbrains.materialdesignapp.model.recycler.Data.Companion.TYPE_MARS


class RecycleFragmentAdapter(
    private var onListItemClickListener: RecycleViewFragment.OnListItemClickListener,
    private var data: MutableList<Pair<Data, Boolean>>,
    private val dragListener: OnStartDragListener
) :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

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

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(
                if (toPosition > fromPosition) toPosition - 1 else toPosition,
                this
            )
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].first.type
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Pair(Data(TYPE_MARS, "", ""), false)
//    private fun generateItem() = Data(TYPE_MARS, "Mars", "")

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {


        override fun bind(data: Pair<Data, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.descriptionTextView).text =
                    data.first.someDescription
                itemView.findViewById<ImageView>(R.id.wikiImageView).setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<Data, Boolean>) {

            val descriptionEditText = itemView.findViewById<EditText>(R.id.marsDescriptionEditText)
            val nameOfNoteTextView = itemView.findViewById<TextView>(R.id.marsTextView)
            val doneButton = itemView.findViewById<Button>(R.id.button_done_description)

            itemView.findViewById<ImageView>(R.id.marsImageView).setOnClickListener {
//                onListItemClickListener.onItemClick(data.first)
                toggleText()
            }
            itemView.findViewById<ImageView>(R.id.addItemImageView).setOnClickListener {
                addItem()
            }
            itemView.findViewById<ImageView>(R.id.removeItemImageView).setOnClickListener { removeItem() }

            itemView.findViewById<ImageView>(R.id.moveItemDown).setOnClickListener {
                moveDown()
            }
            itemView.findViewById<ImageView>(R.id.moveItemUp).setOnClickListener {
                moveUp()
            }

            descriptionEditText.visibility = if (data.second) View.VISIBLE else View.GONE // показать описание если в массиве true

            nameOfNoteTextView.text = getNameOfNote() // отображение названия заметки
            descriptionEditText.setText(getDescriptionOfNote())  // отображение названия заметки

            nameOfNoteTextView.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    // if the event is a key down event on the enter button
                    if (event.action == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER
                    ) {
                        data.first.someText = nameOfNoteTextView.text.toString() // запись нового названия в массив
                        toggleTextForName(data, nameOfNoteTextView.text.toString()) // обновление элемента recycler

                        // clear focus and hide cursor from edit text
                        nameOfNoteTextView.clearFocus()
                        nameOfNoteTextView.isCursorVisible = false

                        return true
                    }
                    return false
                }
            })

            itemView.findViewById<ImageView>(R.id.dragHandleImageView).setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }

            descriptionEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {  // показать кнопку если editText описания в фокусе
                    doneButton.visibility = View.VISIBLE

                    doneButton.setOnClickListener {
                        data.first.someDescription = descriptionEditText.text.toString() // запись нового названия в массив
                        toggleTextForDescription(data, descriptionEditText.text.toString()) // обновление элемента recycler

                        descriptionEditText.clearFocus()
                        descriptionEditText.isCursorVisible = false

                    }
                } else { // если не в фокусе то скрыть кнопку
                    doneButton.visibility = View.GONE
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }


        private fun moveUp() {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }


        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun toggleTextForName(dataElement: Pair<Data, Boolean>, name: String) {
            data[layoutPosition].first.someText = name
//            data[layoutPosition] = dataElement
            notifyItemChanged(layoutPosition)
        }

        private fun toggleTextForDescription(dataElement: Pair<Data, Boolean>, description: String) {
            data[layoutPosition].first.someDescription = description
            notifyItemChanged(layoutPosition)
        }

        private fun getNameOfNote() = data[layoutPosition].first.someText // отображение названия заметки

        private fun getDescriptionOfNote() = data[layoutPosition].first.someDescription // отображение названия заметки

    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            itemView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

}