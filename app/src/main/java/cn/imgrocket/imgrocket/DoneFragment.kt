package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.imgrocket.imgrocket.adapter.DoneRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_done.*

class DoneFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf(
                "item1",
                "item2",
                "item3"
        )
        done_recycler_view.layoutManager = LinearLayoutManager(context)
        done_recycler_view.adapter = DoneRecyclerAdapter(items)
    }
}
