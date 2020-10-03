package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cn.imgrocket.imgrocket.adapter.ProcessingRecyclerAdapter
import cn.imgrocket.imgrocket.databinding.FragmentProcessingBinding
import cn.imgrocket.imgrocket.room.model.TaskItem

class ProcessingFragment : Fragment(), MainActivity.OnProgressUpdateListener {
    private lateinit var binding: FragmentProcessingBinding

    private lateinit var adapter: ProcessingRecyclerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProcessingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items: ArrayList<TaskItem> = ArrayList()
        initListener()
        binding.processingRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ProcessingRecyclerAdapter(items, context!!)
        binding.processingRecyclerView.adapter = adapter
    }

    private fun initListener() {
        binding.fileFabFunction1.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseMultiPhotoActivity::class.java) }
            intent.putExtra("Function", 1)
            startActivity(intent)
        }
        binding.fileFabFunction2.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseSinglePhotoActivity::class.java) }
            intent.putExtra("Function", 2)
            startActivity(intent)
        }
        binding.fileFabFunction3.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseMultiPhotoActivity::class.java) }
            intent.putExtra("Function", 3)

            startActivity(intent)
        }
        binding.fileFabFunction4.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseMultiPhotoActivity::class.java) }
            intent.putExtra("Function", 4)

            startActivity(intent)
        }
        binding.fileFabFunction5.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseSinglePhotoActivity::class.java) }
            intent.putExtra("Function", 5)

            startActivity(intent)
        }
        binding.fileFabFunction6.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseMultiPhotoActivity::class.java) }
            intent.putExtra("Function", 6)
            startActivity(intent)
        }

        (activity as? MainActivity)?.addOnProgressListener(this)
    }

    override fun onProgressUpdated(items: ArrayList<TaskItem>) {
        adapter.onUpdate(items)
    }
}
