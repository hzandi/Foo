package com.sample.foo.common

import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.doIfCurrentDestination(@IdRes destination: Int, action: NavController.()-> Unit){
    if(this.currentDestination?.id == destination){action()}
}
