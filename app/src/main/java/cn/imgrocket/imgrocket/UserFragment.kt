package cn.imgrocket.imgrocket

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.imgrocket.imgrocket.databinding.FragmentUserBinding
import cn.imgrocket.imgrocket.tool.APP
import cn.imgrocket.imgrocket.tool.Function
import cn.imgrocket.imgrocket.tool.URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var global: APP


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        global = activity?.application as APP

        binding.userImageUser.setOnClickListener {
            val intent = Intent()
            if (global.login) {
                activity?.let { it1 -> intent.setClass(it1, UploadAvatarActivity::class.java) }
            } else {
                activity?.let { it1 -> intent.setClass(it1, LoginActivity::class.java) }
            }
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

    public fun refreshAvatar() {
        if (global.login) {
            binding.userTextUsername.text = global.username
            binding.userTextUserNumber.text = global.uid
            Glide
                    .with(this@UserFragment)
                    .load(URL.avatarURL + global.uid + "&version=" + global.avatarVersion)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.userImageUser)
            binding.userImageUser.setColorFilter(Color.TRANSPARENT)
        }

    }
}
