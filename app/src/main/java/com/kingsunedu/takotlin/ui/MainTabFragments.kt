package com.kingsunedu.takotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingsunedu.takotlin.base.BaseFragment
import javax.inject.Inject

class ClassroomFragment @Inject constructor() : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
class PracticeFragment @Inject constructor() : BaseFragment()
class MineFragment @Inject constructor() : BaseFragment()