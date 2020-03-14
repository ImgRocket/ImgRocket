package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.imgrocket.imgrocket.databinding.FragmentUserBinding
import cn.imgrocket.imgrocket.tool.Function

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userImageUser.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, LoginActivity::class.java) }
            startActivity(intent)
        }
        binding.userLayoutItem1.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userLayoutItem2.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userLayoutItem3.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userLayoutItem4.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userLayoutItem5.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
    }

}
