package com.dda.moviespagingdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.dda.moviespagingdemo.R
import com.dda.moviespagingdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportFragmentManager.findFragmentByTag(MovieListFragment::class.java.name) != null) {
            supportFragmentManager.popBackStack(MovieListFragment::class.java.name, 0)
        }
        else {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragmentContainer,
                    MovieListFragment.newInstance(),
                    MovieListFragment::class.java.name
                )
                .commit()
        }
    }
}