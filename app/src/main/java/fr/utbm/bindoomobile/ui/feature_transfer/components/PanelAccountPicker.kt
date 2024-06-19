package fr.utbm.bindoomobile.ui.feature_transfer.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.ui.components.cards.PrimaryCard
import fr.utbm.bindoomobile.ui.components.decoration.SkeletonShape
import fr.utbm.bindoomobile.ui.components.modifiers.dashedBorder
import fr.utbm.bindoomobile.ui.feature_transfer.model.AccountUi
import fr.utbm.bindoomobile.ui.theme.BindooMobileTheme
import fr.utbm.bindoomobile.ui.theme.primaryFontFamily

@Composable
fun PanelAccountPicker(
    isLoading: Boolean,
    selectedAccount: AccountUi?,
    onAccountPickerClick: () -> Unit = {},
    accountSize: DpSize = DpSize(width = 150.dp, height = 60.dp)
) {
    PrimaryCard(
        paddingValues = PaddingValues(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                isLoading -> {

                    SkeletonShape(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(accountSize)
                    )

                    SkeletonShape(
                        modifier = Modifier
                            .height(14.dp)
                            .width(56.dp)
                    )

                    Spacer(
                        Modifier
                            .weight(1f)
                            .height(56.dp)
                    )

                    SkeletonShape(
                        modifier = Modifier
                            .height(24.dp)
                            .width(100.dp)
                            .padding(end = 12.dp)
                    )
                }

                selectedAccount != null -> {
                    Column(
                        modifier = Modifier

                            .padding(end = 10.dp)
                    ) {
                        Text(
                            text = "Compte ${selectedAccount.accountType.asString()}",
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF666666),
                            )
                        )
                        Text(
                            text = selectedAccount.accountNumber,
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF666666)
                            )
                        )
                        Text(
                            text = selectedAccount.bankName,
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF666666)
                            )
                        )
                    }

                    //Spacer(Modifier.width(2.dp))

                    Spacer(Modifier.weight(1f))

                    TextButton(onClick = { onAccountPickerClick() }) {
                        Text(
                            text = selectedAccount.recentBalance, style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF100D40),
                            )
                        )

                        Spacer(Modifier.width(5.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_down_arrrow),
                            contentDescription = "Drop down"
                        )
                    }
                }

                selectedAccount == null -> {

                    Box(
                        Modifier
                            .dashedBorder(
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                cornerRadiusDp = 10.dp
                            )
                            .size(accountSize)
                    )

                    Spacer(Modifier.weight(1f))

                    TextButton(onClick = { onAccountPickerClick() }) {
                        Text(
                            text = stringResource(R.string.choose_account), style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF100D40),
                            )
                        )

                        Spacer(Modifier.width(6.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_down_arrrow),
                            contentDescription = "Drop down"
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PanelCardSelector_Preview() {
    BindooMobileTheme() {
        PanelAccountPicker(
            selectedAccount = AccountUi.mock(), isLoading = false
        )
    }
}

@Composable
@Preview
fun PanelCardSelector_NotSelected_Preview() {
    BindooMobileTheme() {
        PanelAccountPicker(
            selectedAccount = null, isLoading = false
        )
    }
}


@Composable
@Preview
fun PanelCardSelector_Loading_Preview() {
    BindooMobileTheme() {
        PanelAccountPicker(
            selectedAccount = null, isLoading = true
        )
    }
}
