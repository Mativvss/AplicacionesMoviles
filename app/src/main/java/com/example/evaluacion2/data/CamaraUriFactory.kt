package com.example.evaluacion2.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object CamaraUriFactory {
    fun crear(context: Context): Uri {
        val carpeta = File(context.filesDir, "comprobantes").apply { mkdirs() }
        val archivo = File(carpeta, "comprobante_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", archivo)
    }
}
