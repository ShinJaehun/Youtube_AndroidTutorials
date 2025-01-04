package com.shinjaehun.paintdrawingapp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private var floatStartX: Float = -1.0f
    private var floatStartY: Float = -1.0f
    private var floatEndX: Float = -1.0f
    private var floatEndY: Float = -1.0f

    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private val paint : Paint = Paint()


    private companion object{
        //PERMISSION request constant, assign any value
        private const val STORAGE_PERMISSION_CODE = 100
        private const val REQUEST_PERMISSIONS = 23
        private const val REQUEST_PERMISSIONS_33 = 24
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

//        checkPermission()

    }

    private fun drawPaintSketchImage() {
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap!!)
            paint.color = Color.RED
            paint.isAntiAlias = true
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 8F
        }

        canvas?.drawLine(floatStartX, floatStartY-220, floatEndX, floatEndY-220, paint)
        imageView.setImageBitmap(bitmap)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            floatStartX = event.x
            floatStartY = event.y
        }

        if (event?.action == MotionEvent.ACTION_MOVE) {
            floatEndX = event.x
            floatEndY = event.y

            drawPaintSketchImage()

            floatStartX = event.x
            floatStartY = event.y
        }

        if (event?.action == MotionEvent.ACTION_UP) {
            floatEndX = event.x
            floatEndY = event.y

            drawPaintSketchImage()
        }

        return super.onTouchEvent(event)
    }

    fun buttonSaveImage(view: View) {
//        val fileSaveImage = File(
//            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//            Calendar.getInstance().time.toString() + ".jpg",
//        )
//        try {
//            val fileOutPutStream: FileOutputStream = FileOutputStream(fileSaveImage)
//            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fileOutPutStream)
//            fileOutPutStream.flush()
//            fileOutPutStream.close()
//
//            Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

//        val storageManager: StorageManager = getSystemService(STORAGE_SERVICE) as StorageManager
//        val storageVolume: StorageVolume = storageManager.storageVolumes[0]
//        val fileInputImage = File(storageVolume.directory.path + "/Download/images.jpeg")

        var file: File? = null
        val myDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "PaintDrawingApp/")
        if (!myDir.exists()) {
            myDir.mkdir()
        }

        val fname = "Image-" + System.currentTimeMillis() + ".jpg"
        file = File(myDir, fname)
//        Log.i(TAG, "file path: ${myDir.path.toString()} ")

        try {
            val fileOutPutStream = FileOutputStream(file)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fileOutPutStream)
            fileOutPutStream.flush()
            fileOutPutStream.close()

            Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


//    private val permissionLauncherSingle = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        Log.i(TAG, "permissionLauncherSingle: isGranted: $isGranted")
//
//        if (isGranted) {
//            singlePermissionGranted()
//        } else {
//            Log.i(TAG, "permissionLauncherSingle: permission denied...")
//            Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun singlePermissionGranted() {
//        Log.i(TAG, "permissionLauncherSingle: permission granted...")
//        Toast.makeText(this, "permission granted...", Toast.LENGTH_SHORT).show()
//    }
//
//    private val permissionLauncherMultiple = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { result ->
//        var areAllGranted = true
//        for (isGranted in result.values) {
//            Log.d(TAG, "permissionLauncherMultiple: isGranted: $isGranted")
//            areAllGranted = areAllGranted && isGranted
//        }
//
//        if (areAllGranted) {
//
//        } else {
//            Log.i(TAG, "permissionLauncherMultiple: all or some permissions denied...")
//            Toast.makeText(this, "all or some permissions denied...", Toast.LENGTH_SHORT).show()
//
//        }
//    }
//
//    private fun multiplePermissionsGranted() {
//        Log.i(TAG, "permissions denied...")
//        Toast.makeText(this, "permissions granted...", Toast.LENGTH_SHORT).show()
//    }


    private fun checkPermission() {

//        val p = android.Manifest.permission.READ_EXTERNAL_STORAGE
//        permissionLauncherSingle.launch(p)
//        val ps = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        permissionLauncherMultiple.launch(ps)

//        return ContextCompat.checkSelfPermission(
//            this,
//            android.Manifest.permission.READ_EXTERNAL_STORAGE
//        ) == PackageManager.PERMISSION_GRANTED

        var permission = mutableMapOf<String, String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission["readMediaImages"] = android.Manifest.permission.READ_MEDIA_IMAGES
            permission["readMediaAudio"] = android.Manifest.permission.READ_MEDIA_AUDIO
            permission["readMediaVideo"] = android.Manifest.permission.READ_MEDIA_VIDEO
            var denied = permission.count {
                ContextCompat.checkSelfPermission(this, it.value) == PackageManager.PERMISSION_DENIED
            }
            if (denied > 0) {
                requestPermissions(permission.values.toTypedArray(), REQUEST_PERMISSIONS_33)
            }

        } else {
            permission["readExternalStorage"] = android.Manifest.permission.READ_EXTERNAL_STORAGE
            permission["writeExternalStorage"] = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            var denied = permission.count {
                ContextCompat.checkSelfPermission(this, it.value) == PackageManager.PERMISSION_DENIED
            }
            if (denied > 0) {
                requestPermissions(permission.values.toTypedArray(), REQUEST_PERMISSIONS)
            }
        }

//        var permission = mutableMapOf<String, String>()
//        permission["storageRead"] = android.Manifest.permission.READ_EXTERNAL_STORAGE
//        permission["storageWrite"] = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//
//        var denied = permission.count {
//            ContextCompat.checkSelfPermission(this, it.value) == PackageManager.PERMISSION_DENIED
//        }
//
//        showPermissionAlertDialog(permission)
////        if (denied > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
////            requestPermissions(permission.values.toTypedArray(), REQUEST_PERMISSIONS)
////        }
//        if (denied > 0) {
//            requestPermissions(permission.values.toTypedArray(), REQUEST_PERMISSIONS)
//        }

    }

//    private fun showPermissionAlertDialog(permission: Map<String, String>) {
//        AlertDialog.Builder(this)
//            .setTitle("권한 승인이 필요합니다.")
//            .setMessage("권한 승인이 필요하다고~")
//            .setPositiveButton("허용하기") { _, _ ->
//                requestPermissions(permission.values.toTypedArray(), REQUEST_PERMISSIONS)
//            }
//            .setNegativeButton("취소하기") { _, _ ->
//
//            }
//            .create()
//            .show()
//
//    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                REQUEST_PERMISSIONS_33 -> {
                    Toast.makeText(applicationContext, "33 이상 서비스 권한 허용됨.", Toast.LENGTH_SHORT).show()
                }
                REQUEST_PERMISSIONS -> {
                    Toast.makeText(applicationContext, "33 이전 서비스 권한 허용됨.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

//    private fun requestPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            //Android is 11(R) or above
//            try {
//                Log.d(TAG, "requestPermission: try")
//                val intent = Intent()
//                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
//                val uri = Uri.fromParts("package", this.packageName, null)
//                intent.data = uri
//                storageActivityResultLauncher.launch(intent)
//            } catch (e: Exception) {
//                Log.e(TAG, "requestPermission: ", e)
//                val intent = Intent()
//                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
////                storageActivityResultLauncher.launch(intent)
//            }
//        } else {
//            //Android is below 11(R)
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ),
//                STORAGE_PERMISSION_CODE
//            )
//        }
//    }
//
//    private val storageActivityResultLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()){
//        Log.d(TAG, "storageActivityResultLauncher: ")
//        //here we will handle the result of our intent
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//            //Android is 11(R) or above
//            if (Environment.isExternalStorageManager()){
//                //Manage External Storage Permission is granted
//                Log.d(TAG, "storageActivityResultLauncher: Manage External Storage Permission is granted")
////                createFolder()
//            }
//            else{
//                //Manage External Storage Permission is denied....
//                Log.d(TAG, "storageActivityResultLauncher: Manage External Storage Permission is denied....")
//                toast("Manage External Storage Permission is denied....")
//            }
//        }
//        else{
//            //Android is below 11(R)
//        }
//    }


//    private fun createFolder(){
//        //folder name
//        val folderName = folderNameEt.text.toString().trim()
//
//        //create folder using name we just input
//        val file = File("${Environment.getExternalStorageDirectory()}/$folderName")
//        //create folder
//        val folderCreated = file.mkdir()
//
//        //show if folder created or not
//        if (folderCreated) {
//            toast("Folder Created: ${file.absolutePath}")
//        } else {
//            toast("Folder not created....")
//        }
//    }


    private fun toast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}