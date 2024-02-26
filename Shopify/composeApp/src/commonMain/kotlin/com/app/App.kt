package com.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.navigator.Navigator
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.data.storage.UserDataAccess
import com.ns.shopify.presentation.screen.launch.LaunchScreen
import org.koin.compose.getKoin


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


