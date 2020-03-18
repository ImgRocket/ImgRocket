package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.imgrocket.imgrocket.adapter.DoneRecyclerAdapter
import cn.imgrocket.imgrocket.databinding.FragmentDoneBinding

class DoneFragment : Fragment() {
    private lateinit var binding: FragmentDoneBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf(
                "item1",
                "item2",
                "item3"
        )
        binding.doneRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.doneRecyclerView.adapter = DoneRecyclerAdapter(items)
    }
}
