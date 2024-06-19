package fr.utbm.bindoomobile.ui.feature_transfer

import android.os.Build
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.NavigationEventEffect
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.ui.app_host.host_utils.LocalScopedSnackbarState
import fr.utbm.bindoomobile.ui.components.FullscreenProgressBar
import fr.utbm.bindoomobile.ui.components.PrimaryButton
import fr.utbm.bindoomobile.ui.components.ScreenPreview
import fr.utbm.bindoomobile.ui.components.SecondaryToolBar
import fr.utbm.bindoomobile.ui.components.TextBtn
import fr.utbm.bindoomobile.ui.components.dialogs.SuccessDialog
import fr.utbm.bindoomobile.ui.components.forms.DecoratedSearchFormField
import fr.utbm.bindoomobile.ui.components.forms.SimpleFormField
import fr.utbm.bindoomobile.ui.components.header.ScreenHeader
import fr.utbm.bindoomobile.ui.components.snackbar.SnackBarMode
import fr.utbm.bindoomobile.ui.components.text_fields.ReadonlyTextField
import fr.utbm.bindoomobile.ui.core.effects.EnterScreenEffect
import fr.utbm.bindoomobile.ui.core.error.asUiTextError
import fr.utbm.bindoomobile.ui.core.extensions.showToast
import fr.utbm.bindoomobile.ui.core.permissions.AskPermissionResult
import fr.utbm.bindoomobile.ui.core.permissions.CheckPermissionResult
import fr.utbm.bindoomobile.ui.core.permissions.LocalPermissionHelper
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_account.AmountPickersState
import fr.utbm.bindoomobile.ui.feature_account.components.BalanceGridPicker
import fr.utbm.bindoomobile.ui.feature_cards.dialog_card_picker.CardPickerDialog
import fr.utbm.bindoomobile.ui.feature_transfer.components.PanelAccountPicker
import fr.utbm.bindoomobile.ui.theme.primaryFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun SendMoneyScreen(
    viewModel: SendMoneyViewModel = koinViewModel(),
    selectedAccountId: String? = null,
    onBack: () -> Unit
) {
    val snackbarHostState = LocalScopedSnackbarState.current
    val context = LocalContext.current
    val permissionHelper = LocalPermissionHelper.current
    val state = viewModel.state.collectAsStateWithLifecycle().value

    SendMoneyScreen_Ui(
        state = state,
        onIntent = { viewModel.emitIntent(it) },
        onBack = onBack
    )

    EventEffect(
        event = state.accountPickerState.accountSelectErrorEvent,
        onConsumed = viewModel::consumeLoadAccountErrorEvent
    ) {
        snackbarHostState.show(it.asUiTextError().asString(context), SnackBarMode.Negative)
    }

    NavigationEventEffect(
        event = state.requiredBackNavEvent,
        onConsumed = viewModel::consumeBackNavEvent
    ) {
        onBack()
    }

    EnterScreenEffect {
        viewModel.emitIntent(SendMoneyScreenIntent.EnterScreen(selectedAccountId = selectedAccountId))
    }

    EnterScreenEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermission = android.Manifest.permission.POST_NOTIFICATIONS
            val res = permissionHelper.checkIfPermissionGranted(context, notificationPermission)
            when (res) {
                CheckPermissionResult.SHOULD_ASK_PERMISSION -> {
                    permissionHelper.askForPermission(context, notificationPermission) { askRes ->
                        when (askRes) {
                            AskPermissionResult.GRANTED -> {
                                context.showToast(R.string.permission_granted)
                            }

                            AskPermissionResult.REJECTED -> {
                                context.showToast(R.string.permission_rejected)
                            }
                        }
                    }
                }

                // DO nothing as permission is not mandatory for user flow
                CheckPermissionResult.SHOULD_REDIRECT_TO_SETTINGS -> {}
                CheckPermissionResult.PERMISSION_ALREADY_GRANTED -> {}
            }
        }
    }
}


@Composable
private fun SendMoneyScreen_Ui(
    state: SendMoneyScreenState,
    onIntent: (SendMoneyScreenIntent) -> Unit = {},
    onBack: () -> Unit = {}
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader(toolbar = {
                SecondaryToolBar(
                    onBack = { onBack() },
                    title = UiText.StringResource(R.string.send_money),
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(top = 16.dp)
                )
            }) {
                PanelAccountPicker(
                    selectedAccount = state.accountPickerState.selectedAccount,
                    isLoading = state.accountPickerState.isLoading,
                    onAccountPickerClick = {
                        //onIntent(SendMoneyScreenIntent.ToggleAccountPicker(show = true))
                    })
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.send_to),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(16.dp))

            Column(modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()) {

                DecoratedSearchFormField(
                    modifier = Modifier.fillMaxWidth(),
                    fieldTitle = UiText.DynamicString("Account number"),
                    uiField = state.recipientAccount ,
                    onValueChange = {
                        onIntent.invoke(
                            SendMoneyScreenIntent.StringFieldChanged(
                                SendMoneyFieldType.RECIPIENT_ACCOUNT,
                                it
                            )
                        )
                    },
                    onTrailingIconClick = {
                        onIntent.invoke(
                            SendMoneyScreenIntent.SearchRecipient
                        )
                    }
                )

                Column() {
                    Text(
                        text = "Holder account name", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF808289),
                        ), modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(Modifier.height(8.dp))

                    ReadonlyTextField(
                        value = state.recipientName.value,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                        }
                    )
                }

                SimpleFormField(
                    modifier = Modifier.fillMaxWidth(),
                    fieldTitle = UiText.DynamicString("Comment"),
                    uiField = state.comment ,
                    onValueChange = {
                        onIntent.invoke(
                            SendMoneyScreenIntent.StringFieldChanged(
                                SendMoneyFieldType.COMMENT,
                                it
                            )
                        )
                    },
                )

            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.enter_nominal), style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(16.dp))



            if (state.amountState.proposedValues.isNotEmpty()) {
                Spacer(Modifier.height(24.dp))
                BalanceGridPicker(
                    proposedValues = state.amountState.proposedValues, selectedValue = state.amountState.selectedAmount, onValueSelected = {
                        onIntent(SendMoneyScreenIntent.UpdateSelectedValue(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    pickerEnabled = state.amountState.pickersEnabled
                )
            }

            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(36.dp))

            PrimaryButton(
                onClick = { onIntent(SendMoneyScreenIntent.ProceedClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                text = stringResource(R.string.proceed),
                isEnabled = state.proceedButtonEnabled
            )
            TextBtn(
                onClick = { onBack() },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp),
                text = stringResource(id = R.string.cancel)
            )
        }
    }

    if (state.isLoading) {
        FullscreenProgressBar()
    }

    if (state.accountPickerState.showAccountPicker) {
        CardPickerDialog(
            onDismissRequest = { selectedCardId ->
                onIntent(SendMoneyScreenIntent.ToggleAccountPicker(show = false))
                selectedCardId?.let {
                    onIntent(SendMoneyScreenIntent.ChooseAccount(selectedCardId))
                }
            },
            defaultSelectedCard = state.accountPickerState.selectedAccount?.id
        )
    }

    if (state.showSuccessDialog) {
        SuccessDialog(
            title = UiText.StringResource(R.string.transaction_submitted),
            message = UiText.StringResource(R.string.transaction_explanation),
            onDismiss = {
                onIntent(SendMoneyScreenIntent.DismissSuccessDialog)
            }
        )
    }
}

@Composable
@Preview
fun SendMoneyScreen_Preview() {
    ScreenPreview {
        val amounts = setOf(100, 200, 300, 400, 500, 600).map {
            MoneyAmount(it.toFloat())
        }.toSet()

        SendMoneyScreen_Ui(
            SendMoneyScreenState(
                amountState = AmountPickersState(
                    selectedAmount = MoneyAmount(100f),
                    proposedValues = amounts
                ),
            )
        )
    }
}