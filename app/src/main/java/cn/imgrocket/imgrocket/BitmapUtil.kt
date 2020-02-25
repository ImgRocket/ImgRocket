package cn.imgrocket.imgrocket

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import java.io.IOException


object BitmapUtil {

    fun load(context: Context, uri: Uri): Bitmap? {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            } else {
                val columns = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = context.contentResolver?.query(uri, columns, null, null, null)
                if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
                    val path = cursor.getString(cursor.getColumnIndex(columns[0]))
                    cursor.close()
                    return load(path, true)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun load(path: String): Bitmap {
        return BitmapFactory.decodeFile(path)
    }

    /** 从给定的路径加载图片，并指定是否自动旋转方向
     * @param path 图片路径
     * @param adjustOrientation 是否自动旋转方向
     */
    fun load(path: String, adjustOrientation: Boolean): Bitmap {
        if (!adjustOrientation) {
            return load(path)
        } else {
            var bm = load(path)
            var digree = 0
            var exif: ExifInterface?
            try {
                exif = ExifInterface(path)
            } catch (e: IOException) {
                e.printStackTrace()
                exif = null
            }

            if (exif != null) {
                // 读取图片中相机方向信息
                // 计算旋转角度
                digree = when (exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED
                )) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
            }
            if (digree != 0) {
                // 旋转图片
                val m = Matrix()
                m.postRotate(digree.toFloat())
                bm = Bitmap.createBitmap(
                        bm, 0, 0, bm.width,
                        bm.height, m, true
                )
            }
            return bm
        }
    }

    fun Bitmap.cropCenter(): Bitmap {
        val width = this.width
        val height = this.height
        return if (width > height) {
            Bitmap.createBitmap(this, (width - height) / 2, 0, height, height)
        } else {
            Bitmap.createBitmap(this, 0, (height - width) / 2, width, width)
        }
    }

    fun Bitmap.zoom(newWidth: Int, newHeight: Int): Bitmap{
        val width = width
        val height = height
        val scaleWidth = newWidth / width.toFloat()
        val scaleHeight: Float = newHeight / height.toFloat()
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }


}