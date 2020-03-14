package cn.imgrocket.imgrocket.helpfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cn.imgrocket.imgrocket.R
import cn.imgrocket.imgrocket.databinding.FragmentHelp2Binding

class HelpFragment2 : Fragment() {
    private lateinit var binding: FragmentHelp2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHelp2Binding.inflate(inflater, container, false)
        return binding.root
    }

}

