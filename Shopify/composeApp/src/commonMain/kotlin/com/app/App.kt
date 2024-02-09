package com.app

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.app.util.Other
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.data.storage.UserDataAccess
import com.ns.shopify.presentation.componets.AnimVisible
import com.ns.shopify.presentation.componets.CartTabItem
import com.ns.shopify.presentation.componets.TabItem
import com.ns.shopify.presentation.componets.TabItemWithBadgeCount
import com.ns.shopify.presentation.screen.cart.CartTab
import com.ns.shopify.presentation.screen.home.HomeTab
import com.ns.shopify.presentation.screen.launch.LaunchScreen
import com.ns.shopify.presentation.screen.sign_in.SignInScreen
import com.ns.shopify.presentation.screen.more.MoreTab
import com.ns.shopify.presentation.screen.user.UserTab
import com.ns.shopify.presentation.settings.SettingsViewModel
import com.theme.AppTheme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import org.koin.compose.koinInject
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

    // Loading/Refreshing User Data Access
    UserDataAccess.refreshData(rememberCoroutineScope(), getKoin().get<CachingManager>())

    Navigator(LaunchScreen(isDark = isDark))

    /*val vm = rememberKoinInject<SettingsViewModel>()
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
    }*/
}


