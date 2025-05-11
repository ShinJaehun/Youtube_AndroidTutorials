package com.shinjaehun.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val addNoteBtn = findViewById<MaterialButton>(R.id.addnewnotebtn)

        addNoteBtn.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        Realm.init(applicationContext)
        val realm = Realm.getDefaultInstance()
        val noteList = realm.where(Note::class.java).findAll()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val myAdapter = MyAdapter(applicationContext, noteList)
        recyclerView.adapter = myAdapter

        noteList.addChangeListener(object : RealmChangeListener<RealmResults<Note>>{
            override fun onChange(t: RealmResults<Note>) {
                myAdapter.notifyDataSetChanged()
            }

        })
    }
}