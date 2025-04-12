package ui.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutputText(text: String) {
    if (text.isNotBlank()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface.copy(alpha = 0.7f))
                    .clip(RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = text,
                    color = if (text.contains("Success")) {
                        Color(0xFF4CAF50) // Green for success
                    } else if (text.contains("Error")) {
                        MaterialTheme.colors.error
                    } else {
                        MaterialTheme.colors.onSurface
                    },
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun OutputTextPreview() {
    MaterialTheme {
        OutputText("Success: DNS settings updated successfully")
    }
}