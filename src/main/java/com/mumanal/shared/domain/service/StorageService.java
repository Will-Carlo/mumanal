package com.mumanal.shared.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    /**
     * Sube un archivo y retorna la URL pública.
     * @param file El archivo binario.
     * @param folderName Carpeta destino (ej: "products", "users").
     * @return La URL segura (HTTPS) de la imagen.
     */
    String upload(MultipartFile file, String folderName, String customName);

    /**
     * Elimina un archivo (opcional, buena práctica implementarlo).
     * @param imageUrl La URL completa para extraer el ID y borrar.
     */
    void delete(String imageUrl);

    boolean isValidFolder(String folderName);
}