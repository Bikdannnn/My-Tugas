package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GeneralSubmitButton(modifier: Modifier = Modifier, onClick: () -> Unit, text: String ){
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(bottom = 24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2260FF)
        )
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}