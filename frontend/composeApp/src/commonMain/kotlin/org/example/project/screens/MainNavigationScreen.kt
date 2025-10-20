package org.example.project.screens

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.example.project.screens.tabs.ProductCreateTab
import org.example.project.screens.tabs.ProductListTab
import org.example.project.screens.tabs.ProductSearchTab
import org.example.project.screens.tabs.ProductUpdateTab

class MainNavigationScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(
            tab = ProductListTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(
                        ProductListTab,
                        ProductCreateTab,
                        ProductUpdateTab,
                        ProductSearchTab
                    )
                )
            }
        ) {
            Scaffold(
                modifier = Modifier.safeContentPadding(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = it.current.options.title
                            )
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        val tabNavigator: TabNavigator = LocalTabNavigator.current

                        NavigationBarItem(
                            selected = tabNavigator.current.key == ProductListTab.key,
                            label = {
                                Text(
                                    text = ProductListTab.options.title
                                )
                            },
                            icon = {
                                Icon(
                                    painter = ProductListTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                tabNavigator.current = ProductListTab
                            }
                        )

                        NavigationBarItem(
                            selected = tabNavigator.current.key == ProductCreateTab.key,
                            label = {
                                Text(
                                    text = ProductCreateTab.options.title
                                )
                            },
                            icon = {
                                Icon(
                                    painter = ProductCreateTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                tabNavigator.current = ProductCreateTab
                            }
                        )

                        NavigationBarItem(
                            selected = tabNavigator.current.key == ProductUpdateTab.key,
                            label = {
                                Text(
                                    text = ProductUpdateTab.options.title
                                )
                            },
                            icon = {
                                Icon(
                                    painter = ProductUpdateTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                tabNavigator.current = ProductUpdateTab
                            }
                        )

                        NavigationBarItem(
                            selected = tabNavigator.current.key == ProductSearchTab.key,
                            label = {
                                Text(
                                    text = ProductSearchTab.options.title
                                )
                            },
                            icon = {
                                Icon(
                                    painter = ProductSearchTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                tabNavigator.current = ProductSearchTab
                            }
                        )
                    }
                },
                content = {
                    CurrentTab()
                }
            )
        }
    }
}