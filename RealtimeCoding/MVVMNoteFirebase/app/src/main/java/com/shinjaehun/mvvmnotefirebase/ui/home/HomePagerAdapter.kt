package com.shinjaehun.mvvmnotefirebase.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shinjaehun.mvvmnotefirebase.ui.note.NoteListingFragment
import com.shinjaehun.mvvmnotefirebase.ui.task.TaskListingFragment
import com.shinjaehun.mvvmnotefirebase.util.HomeTabs

class HomePagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = HomeTabs.values().size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            HomeTabs.NOTES.index -> NoteListingFragment.newInstance(HomeTabs.NOTES.name)
            HomeTabs.TASKS.index -> TaskListingFragment.newInstance(HomeTabs.TASKS.name)
            else -> throw IllegalStateException("fragment not found")
        }
    }

}