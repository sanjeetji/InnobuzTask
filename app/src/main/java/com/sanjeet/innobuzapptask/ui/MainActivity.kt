package com.sanjeet.innobuzapptask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanjeet.innobuzapptask.databinding.ActivityMainBinding
import com.sanjeet.innobuzapptest.features.posts.ui.fragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}