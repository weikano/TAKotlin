/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kingsunedu.takotlin.di

import androidx.lifecycle.ViewModel
import com.kingsunedu.takotlin.di.FragmentScoped
import com.kingsunedu.takotlin.di.ViewModelKey
import com.kingsunedu.takotlin.ui.ClassroomFragment
import com.kingsunedu.takotlin.ui.MineFragment
import com.kingsunedu.takotlin.ui.MineFragment_Factory
import com.kingsunedu.takotlin.ui.PracticeFragment
import com.kingsunedu.takotlin.vm.LaunchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed for app launch are defined.
 */
@Module
internal abstract class LaunchModule {

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [LaunchViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(LaunchViewModel::class)
    internal abstract fun bindLaunchViewModel(viewModel: LaunchViewModel): ViewModel
}

@Module
internal abstract class PracticeModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributePracticeFragment(): PracticeFragment

}

@Module
internal abstract class ClassroomModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeClassroomFragment(): ClassroomFragment
}

@Module
internal abstract class MineModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMineFragment(): MineFragment
}

//@Module
//internal abstract class MainModule {
//    internal abstract fun bindMainModel(viewModel : MainModel):ViewModel
//}
