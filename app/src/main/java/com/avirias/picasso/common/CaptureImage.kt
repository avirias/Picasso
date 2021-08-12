package com.avirias.picasso.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class CaptureImage : ActivityResultContract<Unit, Bitmap>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap {
        if (resultCode == Activity.RESULT_OK) {
            val extras: Bundle = intent?.extras!!
            return extras["data"] as Bitmap
        }
        throw Throwable("Image not found")
    }
}