package com.restaurant.controller;

import com.restaurant.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
public class ImportController {

    private final CsvService csvService;

    @PostMapping("/restaurant/{restaurantId}")
    public String uploadCsv(@RequestParam("file") MultipartFile file, @PathVariable(value = "restaurantId") Long restaurantId) {
        return csvService.parseAndSave(file, restaurantId);
    }
}
