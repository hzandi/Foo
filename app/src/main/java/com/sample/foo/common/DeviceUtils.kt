package com.sample.foo.common

import android.os.Build
import javax.inject.Inject

class DeviceUtils @Inject constructor() {

    fun isApiLevelAtLeast(apiLevel: Int): Boolean {
        return Build.VERSION.SDK_INT >= apiLevel
    }

}
