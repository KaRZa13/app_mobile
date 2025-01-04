package com.epsi.module_app_mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

open class BaseActivity  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Activity", "############################ onCreate :"+this.localClassName)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Activity", "############################ onStart :"+this.localClassName)
    }

    override fun onPause() {
        super.onPause()
        Log.d("Activity", "############################ onPause :"+this.localClassName)
    }

    override fun onResume() {
        super.onResume()
        Log.d("Activity", "############################ onResume :"+this.localClassName)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Activity", "############################ onRestart :"+this.localClassName)
    }

    override fun onStop() {
        super.onStop()
        Log.d("Activity", "############################ onStop :"+this.localClassName)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity", "############################ onDestroy :"+this.localClassName)
    }
}