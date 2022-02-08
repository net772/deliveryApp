package com.example.deliveryapp.screen.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {
    protected var stateBundle: Bundle? = null

    open fun fetchData(): Job = viewModelScope.launch {  }

    // 뷰의 형태를 저장하기 위한
    // 액티비티, 프래그먼트가 종료되기 전 까지 bundle에 보관
    open fun storeState(stateBundle: Bundle) {
        this.stateBundle = stateBundle
    }



}