package com.shinjaehun.notesapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shinjaehun.notesapp.R
import com.shinjaehun.notesapp.database.NotesDatabase
import com.shinjaehun.notesapp.databinding.ActivityCreateNoteBinding
import com.shinjaehun.notesapp.entities.Note
import com.shinjaehun.notesapp.viewmodels.CreateNoteViewModel
import com.shinjaehun.notesapp.viewmodels.CreateNoteViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 1
        private const val REQUEST_CODE_SELECT_IMAGE = 2
    }

    private lateinit var activityCreateNoteBinding: ActivityCreateNoteBinding
    private lateinit var createNoteViewModel: CreateNoteViewModel
    private var selectedNoteColor: String = "#333333"

    private var selectedImagePath: String = ""
    private var dialogAddURL: AlertDialog? = null

    private var alreadyAvailableNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreateNoteBinding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(activityCreateNoteBinding.root)

        activityCreateNoteBinding.imageBack.setOnClickListener {
            onBackPressed()
        }

        activityCreateNoteBinding.textDateTime.text = SimpleDateFormat("yyyy MMMM dd, EEEE, HH:mm a", Locale.getDefault()).format(Date())

        activityCreateNoteBinding.imageSave.setOnClickListener {
            saveNote()
        }

        if (intent.getBooleanExtra("isViewOrUpdate", false)) {
            alreadyAvailableNote = intent.getSerializableExtra("note") as Note
            setViewOrUpdateNote()
        }

        initMisc()
        setSubtitleIndicatorColor()
    }

    private fun setViewOrUpdateNote(){
        activityCreateNoteBinding.etNoteTitle.setText(alreadyAvailableNote!!.title)
        activityCreateNoteBinding.etNoteSubtitle.setText(alreadyAvailableNote!!.subtitle)
        activityCreateNoteBinding.etNote.setText(alreadyAvailableNote!!.noteText)
        activityCreateNoteBinding.textDateTime.text = alreadyAvailableNote!!.dateTime // 근데 얘는 왜 setText() 없어도 되는거야?
        if (alreadyAvailableNote!!.imagePath != null && !alreadyAvailableNote!!.imagePath!!.trim().isEmpty()) {
            activityCreateNoteBinding.imageNote.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableNote!!.imagePath))
            activityCreateNoteBinding.imageNote.visibility = View.VISIBLE
            selectedImagePath = alreadyAvailableNote!!.imagePath!! // 근데 selectedImagePath를 여기서 저장해둬야 할 필요가 있음?
        }

        if (alreadyAvailableNote!!.webLink != null && !alreadyAvailableNote!!.webLink!!.trim().isEmpty()) {
            activityCreateNoteBinding.textWebUrl.text = alreadyAvailableNote!!.webLink
            activityCreateNoteBinding.layoutWebUrl.visibility = View.VISIBLE
        }

    }
    private fun saveNote() {
        val title = activityCreateNoteBinding.etNoteTitle.text.toString()
        val subtitle = activityCreateNoteBinding.etNoteSubtitle.text.toString()
        val noteText = activityCreateNoteBinding.etNote.text.toString()
        val dateTime = activityCreateNoteBinding.textDateTime.text.toString()

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_SHORT).show()
            return
        } else if (subtitle.trim().isEmpty()) {
            Toast.makeText(this, "Note subtitle can't be empty", Toast.LENGTH_SHORT).show()
            return
        } else if (noteText.trim().isEmpty()) {
            Toast.makeText(this, "Notes can't be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(
            title = title,
            subtitle = subtitle,
            noteText = noteText,
            dateTime = dateTime,
            color = selectedNoteColor,
            imagePath = selectedImagePath
        )

        if (activityCreateNoteBinding.layoutWebUrl.visibility == View.VISIBLE) {
            note.webLink = activityCreateNoteBinding.textWebUrl.text.toString()
        }

        if (alreadyAvailableNote != null) {
            // here, we are setting id of new note from an already available note.
            // since we have set onConflictStrategy to "REPLACE" in NoteDao.
            // this means if id of new note is already available in the db
            // then it will be replaced with new note and our note get updated
            note.id = alreadyAvailableNote!!.id
        }

        val viewModelProviderFactory = CreateNoteViewModelFactory(application)
        createNoteViewModel = ViewModelProvider(this, viewModelProviderFactory).get(CreateNoteViewModel::class.java)
        createNoteViewModel.insertNote(note).also {
//            val intent = Intent()
//            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun initMisc() {
        val layoutMisc : LinearLayout = activityCreateNoteBinding.misc.layoutMisc
        val bottomSheetBehavior: BottomSheetBehavior<LinearLayout> = BottomSheetBehavior.from(layoutMisc)

        activityCreateNoteBinding.misc.tvMiscellaneous.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        val imageColor1 = activityCreateNoteBinding.misc.imageColor1
        val imageColor2 = activityCreateNoteBinding.misc.imageColor2
        val imageColor3 = activityCreateNoteBinding.misc.imageColor3
        val imageColor4 = activityCreateNoteBinding.misc.imageColor4
        val imageColor5 = activityCreateNoteBinding.misc.imageColor5

        activityCreateNoteBinding.misc.imageColor1.setOnClickListener {
            selectedNoteColor = "#333333"
            imageColor1.setImageResource(R.drawable.ic_done)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }

        activityCreateNoteBinding.misc.imageColor2.setOnClickListener {
            selectedNoteColor = "#FF1D8E"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(R.drawable.ic_done)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }

        activityCreateNoteBinding.misc.imageColor3.setOnClickListener {
            selectedNoteColor = "#3a52Fc"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(R.drawable.ic_done)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }

        activityCreateNoteBinding.misc.imageColor4.setOnClickListener {
            selectedNoteColor = "#F3DD5C"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(R.drawable.ic_done)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }

        activityCreateNoteBinding.misc.imageColor5.setOnClickListener {
            selectedNoteColor = "#1AA7EC"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(R.drawable.ic_done)
            setSubtitleIndicatorColor()
        }

        if (alreadyAvailableNote != null && alreadyAvailableNote!!.color != null && !alreadyAvailableNote!!.color!!.trim().isEmpty()) {
            when (alreadyAvailableNote!!.color) {
                "#FF1D8E" -> layoutMisc.findViewById<ImageView>(R.id.image_color2).performClick()
                "#3a52Fc" -> layoutMisc.findViewById<ImageView>(R.id.image_color3).performClick()
                "#F3DD5C" -> layoutMisc.findViewById<ImageView>(R.id.image_color4).performClick()
                "#1AA7EC" -> layoutMisc.findViewById<ImageView>(R.id.image_color5).performClick()
            }
        }

        activityCreateNoteBinding.misc.layoutAddImage.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this@CreateNoteActivity,
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    REQUEST_CODE_STORAGE_PERMISSION
                )
            } else {
                selectImage()
            }
        }

        activityCreateNoteBinding.misc.layoutAddUrl.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            showAddURLDialog()
        }
    }

    private fun setSubtitleIndicatorColor() {
        val gradientDrawable = activityCreateNoteBinding.viewSubtitleIndicator.background as GradientDrawable
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor))
    }

    private fun selectImage() {
        //이렇게 하니까 activity 실행 안되네요~~
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        if (intent.resolveActivity(packageManager) != null) {
//            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
//        }

        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
            it.type = "image/*"
            startActivityForResult(it, REQUEST_CODE_SELECT_IMAGE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage()
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (data != null) {
                val selectedImageUri = data.data
                if (selectedImageUri != null) {
                    try {
                        activityCreateNoteBinding.imageNote.setImageURI(selectedImageUri)
                        activityCreateNoteBinding.imageNote.visibility = View.VISIBLE

                        selectedImagePath = getPathFromUri(selectedImageUri)
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }

                    // 왜 이렇게 bitmap decode 뭘 써 댄거여... 이것 때문에 계속 경고 메시지 떴었는데....

//                    try {
//                        val inputStream: InputStream? = contentResolver.openInputStream(selectedImageUri)
//                        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
//                        activityCreateNoteBinding.imageNote.setImageBitmap(bitmap)
//                        activityCreateNoteBinding.imageNote.visibility = View.VISIBLE
//
//                        selectedImagePath = getPathFromUri(selectedImageUri)
//                    } catch (e: Exception) {
//                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
//                    }
                }
            }
        }
    }

    private fun getPathFromUri(contentUri: Uri): String {
        // contentResolver와 cursor에 대해 공부 필요!
        val filePath: String
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            filePath = contentUri.path.toString()
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    private fun showAddURLDialog() {
        if (dialogAddURL == null) {
            val builder = AlertDialog.Builder(this@CreateNoteActivity)
            val view : View = LayoutInflater.from(this).inflate(
                R.layout.layout_add_url,
                findViewById<ViewGroup>(R.id.layout_addUrlContainer)
            )
            builder.setView(view)
            dialogAddURL = builder.create()
            if (dialogAddURL!!.window != null) {
                dialogAddURL!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            }

            val inputURL = view.findViewById<EditText>(R.id.et_url)
            inputURL.requestFocus()

            view.findViewById<TextView>(R.id.textAdd).setOnClickListener {
                if (inputURL.text.toString().trim().isEmpty()) {
                    Toast.makeText(this@CreateNoteActivity, "Enter URL", Toast.LENGTH_SHORT).show()
                } else if (!Patterns.WEB_URL.matcher(inputURL.text.toString()).matches()) {
                    Toast.makeText(this@CreateNoteActivity, "Enter valid URL", Toast.LENGTH_SHORT).show()
                } else {
                    activityCreateNoteBinding.textWebUrl.text = inputURL.text.toString()
                    activityCreateNoteBinding.layoutWebUrl.visibility = View.VISIBLE
                    dialogAddURL!!.dismiss()
                }
            }

            view.findViewById<TextView>(R.id.textCancel).setOnClickListener {
                dialogAddURL!!.dismiss()
            }
        }

        dialogAddURL!!.show()
    }
}