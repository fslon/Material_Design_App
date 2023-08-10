package ru.geekbrains.materialdesignapp.view.recycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.geekbrains.materialdesignapp.databinding.FragmentRecycleViewBinding
import ru.geekbrains.materialdesignapp.model.recycler.Data

class RecycleViewFragment : Fragment() {
    private var _binding: FragmentRecycleViewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecycleViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arrayListOf(
            Data(Data.TYPE_HEADER, "Header"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_MARS, "Mars", ""),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_MARS, "Mars", null)
        )
        binding.recyclerView.adapter = RecycleFragmentAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(
                        requireContext(), data.someText,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            data
        )
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