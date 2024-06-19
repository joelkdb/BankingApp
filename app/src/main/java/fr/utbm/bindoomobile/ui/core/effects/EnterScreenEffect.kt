package fr.utbm.bindoomobile.ui.core.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope

@Composable
fun EnterScreenEffect(
    block: suspend CoroutineScope.() -> Unit
) {
    val isCompleted = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit, block = {
        if (!isCompleted.value) {
            block()
            isCompleted.value = true
        }
    })
}