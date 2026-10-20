package cl.zaror.arriendos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.zaror.arriendos.ui.AppRaiz
import cl.zaror.arriendos.ui.theme.ZarorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ZarorTheme { AppRaiz() } }
    }
}
