package com.shinjaehun.mvvmnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shinjaehun.mvvmnewsapp.databinding.ActivityMainBinding
import com.shinjaehun.mvvmnewsapp.repository.NewsRepository
import com.shinjaehun.mvvmnewsapp.repository.db.ArticleDatabase
import com.shinjaehun.mvvmnewsapp.viewModel.NewsViewModel
import com.shinjaehun.mvvmnewsapp.viewModel.NewsViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : NewsViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelFactory(newsRepository)

        viewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)

//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        val newsFragment = findViewById<Fragment>(R.id.newsFragment)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}