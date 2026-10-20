package cl.zaror.arriendos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Verde = Color(0xFF2E7D32)
val Rojo = Color(0xFFC62828)
val Ambar = Color(0xFFF9A825)

private val Esquema = lightColorScheme(
    primary = Verde,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC8E6C9),
    secondary = Color(0xFF00695C),
    tertiary = Color(0xFF5D4037),
    background = Color(0xFFFAFDF8),
    surface = Color(0xFFFAFDF8),
    error = Rojo
)

@Composable
fun ZarorTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Esquema, content = content)
}
