package com.mumanal.shared.web.controller;

import com.mumanal.shared.domain.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shared/storage")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

//    @PostMapping("/upload")
//    public ResponseEntity<Map<String, String>> uploadImage(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam(value = "folder", defaultValue = "general") String folder,
//            @RequestParam(value = "customName", required = false) String customName
//    ) {
//        if (!storageService.isValidFolder(folder)) {
//            throw new IllegalArgumentException("Invalid folder destination");
//        }
//
//        String url = storageService.upload(file, folder, customName);
//
//        return ResponseEntity.ok(Map.of("url", url));
//    }
}