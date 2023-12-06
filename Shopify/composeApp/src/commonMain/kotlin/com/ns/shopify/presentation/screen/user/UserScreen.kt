package com.ns.shopify.presentation.screen.user

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.ns.shopify.presentation.componets.CustomDefaultBtn
import com.ns.shopify.presentation.settings.SettingsViewModel
import org.koin.compose.rememberKoinInject

/**
 * Created by Ashwani Kumar Singh on 06,December,2023.
 */
internal class UserScreen: Screen {
    @Composable
    override fun Content() {
        val vm = rememberKoinInject<SettingsViewModel>()
        CustomDefaultBtn (
            shapeSize = 50f,
            btnText = "Logout",
            onClick = {
                vm.saveLoggedInStatus(false)
            }
        )
    }
}