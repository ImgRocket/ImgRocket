package cn.imgrocket.imgrocket

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_choose_single_photo.*
import pub.devrel.easypermissions.EasyPermissions
import android.net.Uri.withAppendedPath
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri


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
                    val selectedImage = data.data!!
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = this.contentResolver?.query(selectedImage, filePathColumn, null, null, null)
                    if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
                        val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                        val baseUri = Uri.parse("content://media/external/images/media")
                        val uri = withAppendedPath(baseUri, "" + id)
                        csp_image_test.setImageURI(uri)
                        cursor.close()
                    }
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
