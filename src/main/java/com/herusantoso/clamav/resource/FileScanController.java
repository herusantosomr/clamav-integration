package com.herusantoso.clamav.resource;

import com.herusantoso.clamav.dto.FileScanResponseDto;
import com.herusantoso.clamav.service.FileScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/scan")
public class FileScanController {

    private static Logger LOGGER = LoggerFactory.getLogger(FileScanController.class);

    @Autowired
    private FileScanService fileScanService;

    @PostMapping
    public ResponseEntity<List<FileScanResponseDto>> uploadFiles(@RequestParam("files") MultipartFile files) {
        List<FileScanResponseDto> res = fileScanService.scanFiles(new MultipartFile[] { files });
        return ResponseEntity.of(Optional.of(res));
    }
}