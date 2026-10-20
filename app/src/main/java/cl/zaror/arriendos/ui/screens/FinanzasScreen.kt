package cl.zaror.arriendos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.zaror.arriendos.data.Movimiento
import cl.zaror.arriendos.data.TipoMovimiento
import cl.zaror.arriendos.ui.theme.Rojo
import cl.zaror.arriendos.ui.theme.Verde
import cl.zaror.arriendos.util.comoPesos
import cl.zaror.arriendos.viewmodel.MovimientoViewModel
import coil.compose.AsyncImage

@Composable
fun FinanzasScreen(vm: MovimientoViewModel, alCrear: () -> Unit) {
    val movimientos by vm.movimientos.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Text(
                "Ingresos y gastos",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
            if (movimientos.isEmpty()) {
                Text(
                    "No hay movimientos.\nRegistra uno con el botón +.",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth().padding(32.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp, 0.dp, 16.dp, 96.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movimientos, key = { it.id }) { FilaMovimiento(it) }
                }
            }
        }
        FloatingActionButton(
            onClick = alCrear,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) { Icon(Icons.Filled.Add, "Nuevo movimiento") }
    }
}

@Composable
private fun FilaMovimiento(m: Movimiento) {
    val esIngreso = m.tipo == TipoMovimiento.INGRESO
    val color = if (esIngreso) Verde else Rojo
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(if (esIngreso) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown, null, tint = color)
            Column(Modifier.weight(1f)) {
                Text(m.descripcion, style = MaterialTheme.typography.bodyLarge)
                Text("${m.categoria} · ${m.fecha}", style = MaterialTheme.typography.bodySmall)
            }
            if (m.comprobanteUri != null) {
                AsyncImage(
                    model = m.comprobanteUri.toUri(),
                    contentDescription = "Comprobante",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp))
                )
            }
            Text(
                (if (esIngreso) "+" else "-") + m.monto.comoPesos(),
                color = color, style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
