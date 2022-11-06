package com.ihfazh.warnain.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChip(
    text: String,
    isActive: Boolean = true,
    onClick: () -> Unit
){

    val color = if (!isActive){
        ChipDefaults.outlinedChipColors(
            contentColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.background
        )
    } else {
        ChipDefaults.chipColors(
            contentColor = MaterialTheme.colors.background,
            backgroundColor = MaterialTheme.colors.primary
        )
    }


    Chip(
        onClick = onClick,
        colors = color,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary)
    ) {
        Text(text = text, fontSize = MaterialTheme.typography.body2.fontSize)
    }

}