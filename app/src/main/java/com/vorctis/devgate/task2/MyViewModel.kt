package com.vorctis.devgate.task2

import androidx.lifecycle.ViewModel
import com.vorctis.devgate.task2.utils.MyUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val utils: MyUtils) : ViewModel() {



}