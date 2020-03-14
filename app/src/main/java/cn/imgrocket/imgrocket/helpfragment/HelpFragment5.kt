package cn.imgrocket.imgrocket.helpfragment

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.imgrocket.imgrocket.tool.Function.toast

import cn.imgrocket.imgrocket.R
import cn.imgrocket.imgrocket.databinding.FragmentHelp5Binding
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream

class HelpFragment5 : Fragment(), EasyPermissions.PermissionCallbacks {
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private lateinit var binding: FragmentHelp5Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHelp5Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPermission()
        binding.help5BtnFinish.setOnClickListener {
            val file = File((context?.filesDir?.path) + "/notNew")
            val output = FileOutputStream(file)
            activity?.finish()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {

    }

    private fun getPermission() {
        if (!EasyPermissions.hasPermissions(context, *permissions)) {
            EasyPermissions.requestPermissions(this, resources.getString(R.string.photo_permission), 1, *permissions)
        }
    }
}

