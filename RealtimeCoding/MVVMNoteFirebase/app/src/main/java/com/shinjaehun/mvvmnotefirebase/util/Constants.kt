package com.shinjaehun.mvvmnotefirebase.util

object FirestoreCollection {
    val NOTE = "fb_note"
    val USER = "fb_user"
}

object FireDatabase {
    val TASK = "fb_task"
}

object FirestoreDocumentField {
    val DATE = "date"
    val USER_ID = "user_id"
}

object SharedPrefConstants {
    val LOCAL_SHARED_PREF = "local_shared_pref"
    val USER_SESSION = "user_session"
}

object StorageConstants {
    val ROOT_DIRECTORY = "firebase_note_app"
    val NOTE_IMAGES = "note_images"
}

enum class HomeTabs(val index: Int, val key: String) {
    NOTES(0, "notes"),
    TASKS(1, "tasks")
}