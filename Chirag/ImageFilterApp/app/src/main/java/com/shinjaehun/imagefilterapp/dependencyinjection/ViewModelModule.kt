package com.shinjaehun.imagefilterapp.dependencyinjection

import com.shinjaehun.imagefilterapp.viewmodels.EditImageViewModel
import com.shinjaehun.imagefilterapp.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) }
    viewModel { SavedImagesViewModel(savedImagesRepository = get()) }
}