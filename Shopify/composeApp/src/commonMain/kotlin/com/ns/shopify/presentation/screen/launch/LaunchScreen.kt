package com.ns.shopify.presentation.screen.launch

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.app.printLog
import com.app.util.Other
import com.ns.shopify.presentation.componets.AnimVisible
import com.ns.shopify.presentation.componets.CartTabItem
import com.ns.shopify.presentation.componets.TabItem
import com.ns.shopify.presentation.screen.cart.CartTab
import com.ns.shopify.presentation.screen.home.HomeTab
import com.ns.shopify.presentation.screen.more.MoreTab
import com.ns.shopify.presentation.screen.sign_in.SignInScreen
import com.ns.shopify.presentation.screen.user.UserTab
import com.ns.shopify.presentation.settings.SettingsViewModel
import com.theme.AppTheme
import org.koin.compose.koinInject

/**
 * Created by Ashwani Kumar Singh on 05,January,2024.
 */
internal data class LaunchScreen(private val isDark: Boolean) : Screen {

    @Composable
    override fun Content() {
        val vm = koinInject<SettingsViewModel>()
        val theme = when(vm.themeIndex) {
            0 -> isDark
            1 -> false
            else -> true
        }

        val count = vm.cartCount

        AppTheme (
            isDark = theme
        ) {
            printLog("App Login Status : ${vm.loggedInStatus}")
            BoxWithConstraints {
                TabNavigator(HomeTab) {
                    if (vm.loggedInStatus) {
                        Scaffold(
                            bottomBar = {
                                AnimVisible(
                                    isVisible = Other.isBottomBarVisible
                                ) {
                                    NavigationBar {
                                        TabItem(HomeTab)
                                        CartTabItem(count=count, tab = CartTab)
                                        TabItem(UserTab)
                                        TabItem(MoreTab)
//                                    TabItem(Settings(vm))
                                    }
                                }
                            },
                            content = {

                                CurrentTab()
                            }
                        )
                    } else {
                        Navigator(SignInScreen())
                    }

                }
            }
        }
    }
}