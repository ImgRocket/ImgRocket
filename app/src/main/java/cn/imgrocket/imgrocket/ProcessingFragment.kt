package cn.imgrocket.imgrocket

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.imgrocket.imgrocket.adapter.ProcessingRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_processing.*

class ProcessingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_processing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items: ArrayList<HashMap<String, Any>> = ArrayList()
        items.add(hashMapOf(Pair("text", "2020-2-7 21:59"), Pair("op", 1), Pair("progress", 45)))
        items.add(hashMapOf(Pair("text", "2020-2-7 21:22"), Pair("op", 0), Pair("progress", 22)))
        items.add(hashMapOf(Pair("text", "2020-2-7 22:22"), Pair("op", 1), Pair("progress", 67)))

        initListener()
        processing_recycler_view.layoutManager = LinearLayoutManager(context)
        processing_recycler_view.adapter = context?.let { ProcessingRecyclerAdapter(items, it) }

    }

    private fun initListener() {
        file_fab_function1.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseMultiPhotoActivity::class.java) }
            intent.putExtra("Function", 1)
            startActivity(intent)
        }
        file_fab_function2.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseSinglePhotoActivity::class.java) }
            intent.putExtra("Function", 2)
            startActivity(intent)
        }
        file_fab_function3.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseMultiPhotoActivity::class.java) }
            intent.putExtra("Function", 3)

            startActivity(intent)
        }
        file_fab_function4.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseMultiPhotoActivity::class.java) }
            intent.putExtra("Function", 4)

            startActivity(intent)
        }
        file_fab_function5.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseSinglePhotoActivity::class.java) }
            intent.putExtra("Function", 5)

            startActivity(intent)
        }
        file_fab_function6.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, ChooseMultiPhotoActivity::class.java) }
            intent.putExtra("Function", 6)
            startActivity(intent)
        }
    }
}
