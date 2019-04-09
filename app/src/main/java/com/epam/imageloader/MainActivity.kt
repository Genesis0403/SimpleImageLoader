package com.epam.imageloader

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadImage()
    }

    private fun loadImage() {
        loadButton.setOnClickListener {
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = INTENT_IMAGE_TYPE
            startActivityForResult(imageIntent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val bitmap = MediaStore.Images.Media.getBitmap(
            contentResolver,
            imageUri
        )
        mainImage.setImageBitmap(bitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            val image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            mainImage.setImageBitmap(image)
        } else {
            Toast.makeText(this, getString(R.string.wrong_result), Toast.LENGTH_SHORT).show()
        }
    }

    private companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val INTENT_IMAGE_TYPE = "image/*"
        private var imageUri: Uri? = null
    }
}
