package co.init.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64


object Base64Utils {

    fun decodeBase64DataToImage(base64Data: String): Bitmap {
        val imageBytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}