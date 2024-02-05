package com.example.news.core.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.safeNavigate(direction: NavDirections, navOptions: NavOptions? = null) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction, navOptions) }
}

fun NavController.safeNavigate(
    @IdRes currentDestinationId: Int, @IdRes id: Int, args: Bundle? = null
) {
    if (currentDestinationId == currentDestination?.id) {
        navigate(id, args)
    }
}
