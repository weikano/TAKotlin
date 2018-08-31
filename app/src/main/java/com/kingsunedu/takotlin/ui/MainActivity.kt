package com.kingsunedu.takotlin.ui

import android.os.Bundle
import androidx.navigation.Navigation
import com.kingsunedu.takotlin.R
import com.kingsunedu.takotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Navigation.findNavController(this, R.id.fragment_container)
    }
}