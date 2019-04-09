package com.epam.imageloader

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("CREATED")
        setContentView(R.layout.activity_main)

        val loadButton = findViewById<Button>(R.id.load_button)
        loadButton.setOnClickListener {
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            startActivityForResult(imageIntent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        main_image.setImageBitmap(savedInstanceState?.get("bitmap") as Bitmap)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val drawable = main_image.drawable as? BitmapDrawable
        outState?.putParcelable("bitmap", drawable?.bitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PICK_IMAGE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val imageUri = data?.data
                    val image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    main_image.setImageBitmap(image)
                }
            }
            else -> Toast.makeText(this, "Wrong result!", Toast.LENGTH_SHORT).show()
        }
    }
}
