package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.imgrocket.imgrocket.adapter.DoneRecyclerAdapter
import cn.imgrocket.imgrocket.databinding.FragmentDoneBinding
import cn.imgrocket.imgrocket.room.model.TaskItem
import kotlinx.android.synthetic.main.fragment_done.*

class DoneFragment : Fragment(), MainActivity.OnProgressUpdateListener {
    private lateinit var binding: FragmentDoneBinding

    private lateinit var adapter: DoneRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items: ArrayList<TaskItem> = ArrayList()
        binding.doneRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = DoneRecyclerAdapter(items)
        binding.doneRecyclerView.adapter = adapter
        (activity as? MainActivity)?.addOnProgressListener(this)
    }

    override fun onProgressUpdated(items: ArrayList<TaskItem>) {
        adapter.onUpdate(items)
    }
}
