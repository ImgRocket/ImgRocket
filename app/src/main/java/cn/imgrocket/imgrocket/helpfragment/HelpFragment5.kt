package cn.imgrocket.imgrocket.helpfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cn.imgrocket.imgrocket.R
import kotlinx.android.synthetic.main.fragment_help5.*

class HelpFragment5 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_help5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        help5_btn_finish.setOnClickListener {
            activity?.finish()
        }
    }
}

