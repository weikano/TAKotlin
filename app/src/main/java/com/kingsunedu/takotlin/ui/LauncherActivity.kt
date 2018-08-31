package com.kingsunedu.takotlin.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kingsunedu.takotlin.base.BaseActivity
import com.kingsunedu.takotlin.utils.EventObserver
import com.kingsunedu.takotlin.utils.checkAllMatched
import com.kingsunedu.takotlin.utils.viewModelProvider
import com.kingsunedu.takotlin.vm.LaunchDestination
import com.kingsunedu.takotlin.vm.LaunchViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LauncherActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel : LaunchViewModel = viewModelProvider()
        viewModel.launchDestination.observe(this, EventObserver {
            when(it) {
                LaunchDestination.MAIN_ACTIVITY -> startActivity(Intent(this, MainActivity::class.java))
                LaunchDestination.LOGIN -> startActivity(Intent(this, LoginActivity::class.java))
            }.checkAllMatched
            finish()
        })
    }
}