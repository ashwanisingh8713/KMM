package com.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.app.util.Other
import com.ns.shopify.presentation.componets.AnimVisible
import com.ns.shopify.presentation.componets.TabItem
import com.ns.shopify.presentation.screen.cart.CartTab
import com.ns.shopify.presentation.screen.home.HomeTab
import com.ns.shopify.presentation.screen.more.MoreTab
import com.ns.shopify.presentation.screen.user.UserTab
import com.ns.shopify.presentation.settings.SettingsViewModel
import com.theme.AppTheme
import org.koin.compose.rememberKoinInject


/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */

/*@Composable
internal fun App(isDark: Boolean) = AppTheme {
    Navigator(HomeScreen())
}*/


@Composable
fun App(
    isDark: Boolean
) {
    val vm = rememberKoinInject<SettingsViewModel>()
    val theme = when(vm.index) {
        0 -> isDark
        1 -> false
        else -> true
    }
    AppTheme (
        isDark = theme
    ) {
        BoxWithConstraints {
            TabNavigator(HomeTab) {
                if (maxWidth.value < 500f) {
                    Scaffold(
                        bottomBar = {
                            AnimVisible(
                                isVisible = Other.isBottomBarVisible
                            ) {
                                NavigationBar {
                                    TabItem(HomeTab)
                                    TabItem(CartTab)
                                    TabItem(UserTab)
                                    TabItem(MoreTab)
//                                    TabItem(Settings(vm))
                                }
                            }
                        }
                    ) {
                        CurrentTab()
                    }
                } else {
                    Box(modifier = Modifier.padding(start = 80.dp)) {
                        CurrentTab()
                    }
                    AnimVisible(
                        isVisible = Other.isBottomBarVisible
                    ) {

                    }
                }
            }
        }
    }
}


