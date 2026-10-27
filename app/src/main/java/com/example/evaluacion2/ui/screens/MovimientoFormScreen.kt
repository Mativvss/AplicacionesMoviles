@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.evaluacion2.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.evaluacion2.data.CamaraUriFactory
import com.example.evaluacion2.data.TipoMovimiento
import com.example.evaluacion2.viewmodel.MovimientoViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun MovimientoFormScreen(
    vm: MovimientoViewModel,
    snackbar: SnackbarHostState,
    alVolver: () -> Unit,
    alExito: () -> Unit
) {
    val f by vm.formulario.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var uriPendiente by remember { mutableStateOf<Uri?>(null) }

    val camara = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
        if (ok) uriPendiente?.let { vm.onComprobante(it.toString()) }
    }
    val permisoCamara = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
        if (concedido) {
            val uri = CamaraUriFactory.crear(context)
            uriPendiente = uri
            camara.launch(uri)
        } else {
            scope.launch { snackbar.showSnackbar("Sin permiso de cámara no se puede adjuntar el comprobante") }
        }
    }
    fun tomarFoto() {
        val concedido = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        if (concedido) {
            val uri = CamaraUriFactory.crear(context)
            uriPendiente = uri
            camara.launch(uri)
        } else {
            permisoCamara.launch(Manifest.permission.CAMERA)
        }
    }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { vm.limpiar(); alVolver() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
            }
            Text("Nuevo movimiento", style = MaterialTheme.typography.headlineSmall)
        }
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ListaDesplegable(
                etiqueta = "Tipo",
                icono = Icons.Filled.SwapVert,
                seleccion = if (f.tipo == TipoMovimiento.GASTO) "Gasto" else "Ingreso",
                opciones = listOf("Gasto", "Ingreso"),
                alElegir = { vm.onTipoChange(if (it == "Gasto") TipoMovimiento.GASTO else TipoMovimiento.INGRESO) }
            )
            ListaDesplegable(
                etiqueta = "Categoría",
                icono = Icons.Filled.Category,
                seleccion = f.categoria,
                opciones = f.categorias,
                alElegir = vm::onCategoriaChange
            )
            CampoConValidacion(
                valor = f.monto, alCambiar = vm::onMontoChange, etiqueta = "Monto (ficticio)",
                icono = Icons.Filled.AttachMoney, error = f.errorMonto, teclado = KeyboardType.Number
            )
            CampoConValidacion(
                valor = f.descripcion, alCambiar = vm::onDescripcionChange, etiqueta = "Descripción",
                icono = Icons.Filled.Description, error = f.errorDescripcion
            )

            OutlinedButton(onClick = ::tomarFoto, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Filled.PhotoCamera, null)
                Text(if (f.comprobanteUri == null) "  Fotografiar comprobante" else "  Volver a tomar la foto")
            }
            if (f.comprobanteUri != null) {
                AsyncImage(
                    model = f.comprobanteUri!!.toUri(),
                    contentDescription = "Vista previa del comprobante",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp))
                )
            }
            Button(onClick = { vm.guardar(alExito) }, modifier = Modifier.fillMaxWidth()) {
                Text("Guardar movimiento")
            }
        }
    }
}

@Composable
private fun ListaDesplegable(
    etiqueta: String,
    icono: ImageVector,
    seleccion: String,
    opciones: List<String>,
    alElegir: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expandido, onExpandedChange = { expandido = it }) {
        OutlinedTextField(
            value = seleccion,
            onValueChange = {},
            readOnly = true,
            label = { Text(etiqueta) },
            leadingIcon = { Icon(icono, null) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = { alElegir(opcion); expandido = false }
                )
            }
        }
    }
}

@Composable
private fun CampoConValidacion(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    icono: ImageVector,
    error: String?,
    teclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        leadingIcon = { Icon(icono, null) },
        trailingIcon = {
            when {
                error != null -> Icon(Icons.Filled.Error, "Campo con error", tint = MaterialTheme.colorScheme.error)
                valor.isNotBlank() -> Icon(Icons.Filled.CheckCircle, "Campo válido")
            }
        },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        modifier = Modifier.fillMaxWidth()
    )
}
