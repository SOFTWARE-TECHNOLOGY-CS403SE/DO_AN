package org.example.advancedrealestate_be.controller.api.auth;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/user")
public class FileController {

    private static final String IMAGE_DIRECTORY = "IMAGE";
    private static final String IMAGE_DIRECTORY1 = "uploads/images/";

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(IMAGE_DIRECTORY).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Không thể đọc file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Lỗi khi tải file: " + fileName, e);
        }
    }

    @GetMapping("/building/{fileName:.+}")
    public ResponseEntity<Resource> getFileBuiding(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("uploads/buiding/images").resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Không thể đọc file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Lỗi khi tải file: " + fileName, e);
        }
    }
}

