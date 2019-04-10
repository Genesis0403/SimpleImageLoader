package com.epam.imageloader

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Class which loads image to [mainImage] from gallery on [loadButton] press.
 *
 * @author Vlad Korotkevich
 */

class MainActivity : AppCompatActivity() {

    private var imageUri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadButton.setOnClickListener {
            loadImage()
        }
    }

    private fun loadImage() {
        val imageIntent = Intent(Intent.ACTION_PICK)
        imageIntent.type = INTENT_IMAGE_TYPE
        startActivityForResult(imageIntent, PICK_IMAGE_REQUEST)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        imageUri = savedInstanceState?.get(IMAGE_URI) as? Uri
        setImageBitmap()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(IMAGE_URI, imageUri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            setImageBitmap()
        } else {
            Toast.makeText(this, getString(R.string.wrong_result), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setImageBitmap() {
        if (imageUri == Uri.EMPTY || imageUri == null) return
        val image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        mainImage.setImageBitmap(image)
    }

    private companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val INTENT_IMAGE_TYPE = "image/*"
        private const val IMAGE_URI = "imageUri"
    }
}
