package com.yefri.core.utils

import io.ktor.http.content.*
import java.lang.Exception
import java.nio.file.Path
import kotlin.io.path.writeBytes

suspend fun saveImage(multipartData: MultiPartData) {
    multipartData.forEachPart { part ->
        when (part) {
            is PartData.FileItem-> {
                val fileName = part.originalFileName as String
                val fileBytes = part.streamProvider().readBytes()
                val path = Path.of("images/${fileName}")
                path.writeBytes(fileBytes)
            } else -> throw Exception("El formato del archivo no es válido")
        }
        part.dispose()
    }
}