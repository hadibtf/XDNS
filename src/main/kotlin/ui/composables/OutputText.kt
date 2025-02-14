package ui.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutputText(text: String = "") {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 12.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            fontSize = 12.sp,
            color = if (text.startsWith("Error")) Color.Red else MaterialTheme.colors.onBackground
        )
    }
}

@Composable
@Preview
fun OutputTextPreview() {
    OutputText("Hello World")
}