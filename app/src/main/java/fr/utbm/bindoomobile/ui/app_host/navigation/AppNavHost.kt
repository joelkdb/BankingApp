package fr.utbm.bindoomobile.ui.app_host.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import fr.utbm.bindoomobile.ui.app_host.navigation.graphs.createAppLockGraph
import fr.utbm.bindoomobile.ui.app_host.navigation.model.ConditionalNavigation
import fr.utbm.bindoomobile.ui.app_host.navigation.model.NavDestinations
import fr.utbm.bindoomobile.ui.core.effects.EnterScreenEffect
import fr.utbm.bindoomobile.ui.feature_account.components.account_actions.AccountAction
import fr.utbm.bindoomobile.ui.feature_app_settings.AppSettingsScreen
import fr.utbm.bindoomobile.ui.feature_cards.screen_add_card.AddCardScreen
import fr.utbm.bindoomobile.ui.feature_cards.screen_card_details.CardDetailsScreen
import fr.utbm.bindoomobile.ui.feature_cards.screen_card_list.CardListScreen
import fr.utbm.bindoomobile.ui.feature_home.HomeScreen
import fr.utbm.bindoomobile.ui.feature_login.LoginScreen
import fr.utbm.bindoomobile.ui.feature_onboarding.OnboardingScreen
import fr.utbm.bindoomobile.ui.feature_profile.ProfileScreen
import fr.utbm.bindoomobile.ui.feature_profile.menu.MenuEntry
import fr.utbm.bindoomobile.ui.feature_transactions.TransactionHistoryScreen
import fr.utbm.bindoomobile.ui.feature_transfer.SendMoneyScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    conditionalNavigation: ConditionalNavigation,
    paddingValues: PaddingValues
) {

    // Conditional navigation
    EnterScreenEffect {
        if (conditionalNavigation.requireLogin) {
            navController.navigate(NavDestinations.Login.route) {
                popUpTo(NavDestinations.RootGraph.route) {
                    inclusive = true
                }
            }

            if (conditionalNavigation.requireOnboarding) {
                navController.navigate(NavDestinations.Onboarding.route) {
                    popUpTo(NavDestinations.Login.route) {
                        inclusive = true
                    }
                }
            }
        } else {
            if (conditionalNavigation.requireCreateAppLock) {
                navController.navigate(NavDestinations.SetupAppLockGraph.route) {
                    popUpTo(NavDestinations.RootGraph.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavDestinations.RootGraph.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        createAppLockGraph(navController)

        composable(NavDestinations.Login.route) {
            LoginScreen(
                onLoginCompleted = {
                    navController.navigate(route = NavDestinations.SetupAppLockGraph.route) {
                        popUpTo(NavDestinations.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToSignUp = {
                    // TODO
                }
            )
        }

        composable(NavDestinations.Onboarding.route) {
            OnboardingScreen(
                onGoToLogin = {
                    navController.navigate(NavDestinations.Login.route) {
                        popUpTo(NavDestinations.Onboarding.route) {
                            inclusive = true
                        }
                    }
                },
                onSignUp = {
                    //TODO
                }
            )
        }

        navigation(
            startDestination = NavDestinations.RootGraph.Home.route,
            route = NavDestinations.RootGraph.route
        ) {
            composable(NavDestinations.RootGraph.Home.route) {
                HomeScreen(
                    onGoToDestination = { navEntry ->
                        if (navEntry in listOf(
                                NavDestinations.RootGraph.CardList,
                                NavDestinations.RootGraph.AddCard
                            )
                        ) {
                            navController.navigate(navEntry.route)
                        }
                    },
                    onCardDetails = { cardId ->
                        val route = NavDestinations.RootGraph.CardDetails.route
                        navController.navigate("${route}/${cardId}")
                    },
                    onAccountAction = {
                        when (it) {
                            AccountAction.TopUp -> {
                                navController.navigate(NavDestinations.RootGraph.AccountTopUp.route)
                            }

                            AccountAction.SendMoney -> {
                                navController.navigate(NavDestinations.RootGraph.AccountSend.route)
                            }

                            // TODO
                            AccountAction.Pay -> {}
                            AccountAction.RequestMoney -> {}
                        }
                    }
                )
            }

            composable(NavDestinations.RootGraph.TransactionHistory.route) {
                TransactionHistoryScreen()
            }

            composable(NavDestinations.RootGraph.Profile.route) {
                ProfileScreen(
                    onLogoutCompleted = {
                        navController.navigate(NavDestinations.Login.route) {
                            popUpTo(NavDestinations.RootGraph.route) {
                                inclusive = true
                            }
                        }
                    },
                    onMenuEntry = {
                        val route = when (it) {
                            MenuEntry.Help -> NavDestinations.RootGraph.Help.route
                            MenuEntry.AppSettings -> NavDestinations.RootGraph.AppSettings.route
                        }

                        navController.navigate(route)
                    }
                )
            }

            composable(NavDestinations.RootGraph.CardList.route) {
                CardListScreen(
                    onAddCard = {
                        navController.navigate(NavDestinations.RootGraph.AddCard.route)
                    },
                    onCardDetails = { cardId ->
                        navController.navigate("${NavDestinations.RootGraph.CardDetails.route}/${cardId}")
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(NavDestinations.RootGraph.AddCard.route) {
                AddCardScreen(onBack = {
                    navController.popBackStack()
                })
            }

            composable(
                route = "${NavDestinations.RootGraph.CardDetails.route}/{cardId}",
                arguments = listOf(navArgument("cardId") { type = NavType.StringType })
            ) {
                val cardId = it.arguments?.getString("cardId")!!

                CardDetailsScreen(
                    cardId = cardId,
                    onBack = {
                        navController.popBackStack()
                    },
                    onAccountAction = { action ->
                        when (action) {
                            AccountAction.Pay -> {

                            }

                            AccountAction.RequestMoney -> {

                            }

                            AccountAction.SendMoney -> {
                                navController.navigate(NavDestinations.RootGraph.AccountSend.route)
                            }

                            AccountAction.TopUp -> {

                            }
                        }
                    }
                )
            }

            composable(
                route = "${NavDestinations.RootGraph.AccountTopUp.route}?selectedCard={selectedCard}",
                arguments = listOf(
                    navArgument("selectedCard") {
                        nullable = true
                        defaultValue = null
                        type = NavType.StringType
                    }
                )
            ) {
                val selectedCard = it.arguments?.getString("selectedCard")

            }


            composable(
                route = "${NavDestinations.RootGraph.AccountSend.route}?selectedAccount={selectedAccount}",
                arguments = listOf(
                    navArgument("selectedAccount") {
                        nullable = true
                        defaultValue = null
                        type = NavType.StringType
                    }
                )
            ) {
                val selectedCard = it.arguments?.getString("selectedAccount")

                SendMoneyScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    selectedAccountId = selectedCard
                )
            }

            composable(NavDestinations.RootGraph.Help.route) {

            }

            composable(NavDestinations.RootGraph.AppSettings.route) {
                AppSettingsScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

