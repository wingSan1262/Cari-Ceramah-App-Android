package com.example.basecomponent.base_component

import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivityCompose : DaggerAppCompatActivity() {


    open var isStatusBarTransparant : Boolean = false

    fun setStatusBarAs (){
        if(isStatusBarTransparant)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarAs()
    }
}