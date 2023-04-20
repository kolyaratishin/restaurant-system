package com.restaurant.controller;

import com.restaurant.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/export")
public class ExportController {

    private final CsvService csvService;

    @GetMapping("/csv/{restaurantId}")
    public ResponseEntity<ByteArrayResource> exportToCsv(@PathVariable(value = "restaurantId") Long restaurantId) {
        byte[] bytes = csvService.export(restaurantId);
        ByteArrayResource resource = new ByteArrayResource(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=export.csv");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
