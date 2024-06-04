package com.tubes.travel_insight.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tubes.travel_insight.ui.theme.Green40

@Composable
fun CustomButton(buttonText: String, onclick: () -> Unit) {
    Button(onClick = onclick) {
        Text(
            text = buttonText,
            color = Color.White
        )
    }
}

@Composable
fun CustomButtonWhite(buttonText: String, onclick: () -> Unit) {
    Button(
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Green40
        ),
    ) {
        Text(
            text = buttonText,
            color = Green40
        )
    }
}

@Composable
fun CustomUpdateButton(buttonText: String, onclick: () -> Unit) {
    Button(onClick = onclick, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = buttonText,
            color = Color.White
        )
    }
}

@Composable
fun TitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp),
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Monospace,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}
