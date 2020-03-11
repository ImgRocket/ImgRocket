package cn.imgrocket.imgrocket.helpfragment

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.imgrocket.imgrocket.tool.Function.toast

import cn.imgrocket.imgrocket.R
import kotlinx.android.synthetic.main.fragment_help5.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream

class HelpFragment5 : Fragment(), EasyPermissions.PermissionCallbacks {
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_help5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPermission()
        help5_btn_finish.setOnClickListener {
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

