package com.example.firewatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.firewatch.config.get
import com.example.firewatch.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val mainViewModel = ViewModelProvider.get(this, MainViewModel(FireWatchApplication.appModule.profileRepository, FireWatchApplication.appModule.appContext))
        mainViewModel.login()
    }
}


