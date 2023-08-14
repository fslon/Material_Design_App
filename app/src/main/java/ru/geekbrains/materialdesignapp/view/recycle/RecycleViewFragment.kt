package ru.geekbrains.materialdesignapp.view.recycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.materialdesignapp.databinding.FragmentRecycleViewBinding
import ru.geekbrains.materialdesignapp.model.recycler.Data

class RecycleViewFragment : Fragment() {
    private var _binding: FragmentRecycleViewBinding? = null
    private val binding get() = _binding!!
    lateinit var itemTouchHelper: ItemTouchHelper


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecycleViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arrayListOf(
            Pair(Data(Data.TYPE_MARS, "", ""), false)
        )

        val adapter = RecycleFragmentAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(
                        requireContext(), data.someText,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            data,
            object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener { adapter.appendItem() }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = RecycleViewFragment()
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }


}