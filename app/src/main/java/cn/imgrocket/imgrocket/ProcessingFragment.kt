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

class ProcessingFragment : Fragment() {
    private lateinit var binding: FragmentProcessingBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProcessingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items: ArrayList<HashMap<String, Any>> = ArrayList()
        items.add(hashMapOf(Pair("text", "2020-2-7 21:59"), Pair("op", 1), Pair("progress", 45)))
        items.add(hashMapOf(Pair("text", "2020-2-7 21:22"), Pair("op", 0), Pair("progress", 22)))
        items.add(hashMapOf(Pair("text", "2020-2-7 22:22"), Pair("op", 1), Pair("progress", 67)))

        initListener()
        binding.processingRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.processingRecyclerView.adapter = context?.let { ProcessingRecyclerAdapter(items, it) }

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
    }
}
