package com.shinjaehun.notesapp

import io.realm.RealmObject

open class Note: RealmObject() {
    var title: String = ""
    var description: String = ""
    var createdTime: Long = 0
}
