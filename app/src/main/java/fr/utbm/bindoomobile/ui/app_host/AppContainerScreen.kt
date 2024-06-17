package fr.utbm.bindoomobile.ui.app_host

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.ui.app_host.host_utils.LocalScopedSnackbarState
import fr.utbm.bindoomobile.ui.app_host.host_utils.ScopedSnackBarState
import fr.utbm.bindoomobile.ui.app_host.navigation.AppBottomNav
import fr.utbm.bindoomobile.ui.app_host.navigation.AppNavHost
import fr.utbm.bindoomobile.ui.app_host.navigation.model.NavDestinations
import fr.utbm.bindoomobile.ui.components.DotsProgressIndicator
import fr.utbm.bindoomobile.ui.components.ScreenPreview
import fr.utbm.bindoomobile.ui.components.error.ErrorFullScreen
import fr.utbm.bindoomobile.ui.components.snackbar.ResultSnackBar
import fr.utbm.bindoomobile.ui.core.permissions.LocalPermissionHelper
import fr.utbm.bindoomobile.ui.core.permissions.PermissionHelper
import fr.utbm.bindoomobile.ui.feature_app_lock.lock_screen.LockScreen
import fr.utbm.bindoomobile.ui.theme.ubuntuFontFamily
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AppContainerScreen(viewModel: AppViewModel = koinViewModel()) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val shouldShowBottomNav = NavDestinations
        .primaryDestinationsRoutes()
        .contains(navBackStackEntry?.destination?.route)

    val snackBarHostState = remember { SnackbarHostState() }
    val hostCoroutineScope = rememberCoroutineScope()

    val permissionHelper = koinInject<PermissionHelper>()

    val state = viewModel.appState.collectAsStateWithLifecycle().value

    when (state) {
        is AppState.Loading -> {
            AppLoadingScreen()
        }

        is AppState.Ready -> {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState) { ResultSnackBar(snackbarData = it) }
                },
                bottomBar = { if (shouldShowBottomNav) AppBottomNav(navController) }
            ) { pv ->

                CompositionLocalProvider(
                    LocalScopedSnackbarState provides ScopedSnackBarState(
                        value = snackBarHostState,
                        coroutineScope = hostCoroutineScope
                    ),
                    LocalPermissionHelper provides permissionHelper
                ) {
                    if (state.requireUnlock) {
                        LockScreen(
                            onAppUnlock = {
                                viewModel.emitIntent(AppIntent.TryPostUnlock)
                            },
                            onLogoutSucceeded = {
                                viewModel.emitIntent(AppIntent.AppLockLogout)
                            }
                        )
                    } else {
                        AppNavHost(
                            navController = navController,
                            conditionalNavigation = state.conditionalNavigation,
                            paddingValues = pv
                        )
                    }
                }
            }
        }

        is AppState.InitFailure -> {
            ErrorFullScreen(
                error = state.error,
                onRetry = { viewModel.emitIntent(AppIntent.EnterApp) }
            )
        }
    }
}


@Composable
fun AppLoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_bindoo_logo
            ),
            contentDescription = "Logo",
            modifier = Modifier
                .size(275.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = ubuntuFontFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.scrim,
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        DotsProgressIndicator(
            circleSize = 15.dp,
            travelDistance = 20.dp,
            //circleColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
@Preview
fun AppLoadingScreen_Preview() {
    ScreenPreview {
        AppLoadingScreen()
    }
}