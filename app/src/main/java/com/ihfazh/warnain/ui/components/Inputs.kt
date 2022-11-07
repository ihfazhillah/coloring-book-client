package com.ihfazh.warnain.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ihfazh.warnain.R

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    label: String? = null,
    onChange: (String) -> Unit = {},
    onIconClicked: () -> Unit = {},
    iconPainter: Painter? = null,
    value: String,
    placeHolder: String = ""
){

    Column(modifier = modifier) {
        if (label != null) {
            Text(text = label, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(10.dp))
        }


        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            trailingIcon = @Composable {
                if (iconPainter != null) {
                    Image(
                        iconPainter,
                        contentDescription = "Test",
                        modifier = Modifier
                            .clickable(onClick = onIconClicked)
                            .height(24.dp)
                            .width(24.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = @Composable {
                Text(placeHolder, fontSize = 14.sp)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.primary
            )
        )
    }
}

@Composable
fun Stepper(
    modifier: Modifier = Modifier,
    onChange : (Int) -> Unit,
    value: Int
) {

    var numberText by remember {
        mutableStateOf(value)
    }

    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
    ) {

        Button(
            onClick = {
                numberText -= 1
                onChange.invoke(numberText)
            },
            modifier = Modifier
                .height(TextFieldDefaults.MinHeight)
                .clip(RectangleShape),
            shape = RectangleShape,
            enabled = numberText > 1
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_horizontal_rule_24),
                contentDescription = "Decrease"
            )
        }

        OutlinedTextField(
            shape = RectangleShape,
            value = numberText.toString(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onBackground
            ),
            onValueChange = {
                if (it.isEmpty()) {
                    numberText = 1
                } else {
                    numberText = it.toInt()
                }
                onChange.invoke(numberText)
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.widthIn(32.dp, 64.dp)
        )
        Button(
            modifier = Modifier
                .height(TextFieldDefaults.MinHeight),
            shape = RectangleShape,
            onClick = {
                numberText += 1
                onChange.invoke(numberText)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "Increase"
            )
        }

    }
}