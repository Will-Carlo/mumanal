package com.mumanal.shared.domain.service;

import com.mumanal.shared.domain.exception.BadRequestException;
import com.mumanal.shared.domain.exception.FileStorageException;
import com.mumanal.shared.domain.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${app.application.name}")
    private String applicationName;

    public StorageServiceImpl() {
    }

    @Override
    public String upload(MultipartFile file, String folderName, String customName) {
//        // 1. Validaciones Básicas
//        if (file.isEmpty()) {
//            throw new BadRequestException("Cannot upload empty file");
//        }
//        if (!isImage(file)) {
//            throw new BadRequestException("File is not a valid image");
//        }
//
//        try {
//            String fileName;
//
//            // Lógica de Nombrado Profesional
//            if (customName != null && !customName.isBlank()) {
//                // 1. Sanitizar: "Paquete Tarija! @2026" -> "paquete-tarija-2026"
//                String slug = customName.toLowerCase()
//                        .replaceAll("[^a-z0-9\\s-]", "") // Quitar caracteres raros
//                        .replaceAll("\\s+", "-"); // Espacios a guiones
//
//                // 2. Agregar random corto para unicidad (4 caracteres es suficiente)
//                String shortRandom = UUID.randomUUID().toString().substring(0, 5);
//
//                fileName = slug + "-" + shortRandom;
//            } else {
//                // Fallback: Si no mandaron nombre, usamos UUID completo
//                fileName = UUID.randomUUID().toString();
//            }
//
//            String path = applicationName + "/" + folderName + "/" + fileName;
//
////            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
////                    "public_id", path,
////                    "overwrite", false,
////                    "resource_type", "image"
////            ));
//
//            return uploadResult.get("secure_url").toString();
//
//        } catch (IOException e) {
//            throw new FileStorageException("Failed to upload file", e);
//        }
        return "";
    }

    @Override
    public void delete(String imageUrl) {
        try {
            // Extraer el public_id de la URL es complejo porque incluye versión y extensión.
            // Para simplificar, en un sistema real guardarías el 'public_id' en la BD además de la URL.
            // Por ahora, dejaremos esto pendiente o usaremos un parser simple si es crítico.

            // Ejemplo de extracción simple (Frágil):
            // Url: https://.../mumanal/products/123.jpg -> PublicId: mumanal/products/123
            // cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            // Loguear error pero no detener flujo
            e.printStackTrace();
        }
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/"));
    }

    @Override
    public boolean isValidFolder(String folder) {
//        if (folder == null || allowedFolders == null) {
//            return false;
//        }
//
//        return allowedFolders.contains(folder);
        return false;
    }
}