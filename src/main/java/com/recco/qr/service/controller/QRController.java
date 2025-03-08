package com.recco.qr.service.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recco.qr.service.services.QRService;


@RestController
@RequestMapping("/api/qrcode")
public class QRController {
    @Autowired
    private QRService qrService;

    // Generate QR Code for a table
    @PostMapping("/generate")
    public ResponseEntity<String> generateQRCode(@RequestParam String tableId) {
        try {
            String qrCodeUrl = qrService.generateQRForTable(tableId);
            return ResponseEntity.ok(qrCodeUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating QR Code");
        }
    }

    @GetMapping(value = "/{tableId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getQRCode(@PathVariable String tableId) {
        try {
            String filePath = "./qrcodes/" + tableId + ".png"; // Path to stored QR code
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + tableId + ".png\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

