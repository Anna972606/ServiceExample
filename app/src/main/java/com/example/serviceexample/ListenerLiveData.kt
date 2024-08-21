package com.example.serviceexample

import android.graphics.Bitmap
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

object ListenerLiveData : MutableLiveData<Pair<Int, Bitmap?>>() {

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in Pair<Int, Bitmap?>?>) {
        super.observe(owner, Observer { data ->
            if (data == null) return@Observer
            observer.onChanged(data)
            value = null
        })
    }

    fun postEvent(data: Pair<Int, Bitmap?>) {
        postValue(data)
    }
}