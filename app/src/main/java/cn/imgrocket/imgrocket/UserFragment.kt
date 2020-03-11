package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.imgrocket.imgrocket.tool.Function
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_image_user.setOnClickListener {
            val intent = Intent()
            activity?.let { it1 -> intent.setClass(it1, LoginActivity::class.java) }
            startActivity(intent)
        }
        user_layout_item1.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        user_layout_item2.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        user_layout_item3.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        user_layout_item4.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        user_layout_item5.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
    }

}
