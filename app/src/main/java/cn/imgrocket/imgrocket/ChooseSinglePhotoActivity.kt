package cn.imgrocket.imgrocket

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_choose_single_photo.*
import pub.devrel.easypermissions.EasyPermissions

class ChooseSinglePhotoActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        Toast.makeText(this, "不给权限弄死你", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {}

    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_single_photo)
        getPicture()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                RESULT_LOAD_IMAGE -> {
                    data.data?.let { uri -> BitmapUtil.load(this, uri)?.also { csp_image_test.setImageBitmap(it) } }
                }
            }
        }
    }

    private fun getPicture() {
        getPermission()
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, RESULT_LOAD_IMAGE)
    }

    private fun getPermission() {
        if (!EasyPermissions.hasPermissions(this, *permissions)) {
            EasyPermissions.requestPermissions(this, resources.getString(R.string.photo_permission), 1, *permissions)
        }
    }

    companion object {
        const val RESULT_LOAD_IMAGE = 10
    }
}
