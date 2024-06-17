package fr.utbm.bindoomobile.ui.feature_cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.utbm.bindoomobile.ui.feature_cards.helpers.CardUiHelpers
import fr.utbm.bindoomobile.ui.feature_cards.screen_add_card.AddCardState
import fr.utbm.bindoomobile.ui.feature_cards.screen_add_card.UiField
import fr.utbm.bindoomobile.ui.components.text_fields.PrimaryTextField
import fr.utbm.bindoomobile.ui.theme.BindooMobileTheme
import fr.utbm.bindoomobile.ui.theme.primaryFontFamily

// TODO refactoring of fields
@Composable
fun CardNumberField(
    title: String,
    cardNumber: UiField,
    onPostValue: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    type: KeyboardType = KeyboardType.Text,
) {

    Column(modifier = modifier) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF808289),
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        PrimaryTextField(
            value = cardNumber.value,
            onValueChange = {
                onPostValue.invoke(it)
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = type,
            ),
            visualTransformation = { number ->
                CardUiHelpers.formatCardNumber(number)
            },
            trailingIcon = {
                Box(
                    Modifier
                        .padding(end = 10.dp)
                        .heightIn(max = 32.dp)) {
                    SmallCardIcon()
                }
            },
            error = cardNumber.error?.asString()
        )
    }
}


@Preview
@Composable
fun CardTextField_Preview() {
    BindooMobileTheme() {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CardNumberField(
                title = "Card Number",
                onPostValue = {},
                cardNumber = AddCardState.mock().formFields.cardNumber,
            )
        }
    }
}