package com.syamgith.chatsnapz

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CreateSnapActivity : AppCompatActivity() {

    var createSnapImageView: ImageView? = null
    var messageEditText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)
    }

    fun getPhoto() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    fun chooseImageClicked() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            getPhoto()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImage = data!!.data
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                createSnapImageView?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }
}
