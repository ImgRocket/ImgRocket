package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_processing.*

class ProcessingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_processing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
