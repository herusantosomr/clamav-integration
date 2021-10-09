package com.herusantoso.clamav.service;

import com.herusantoso.clamav.dto.FileScanResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileScanService {

    List<FileScanResponseDto> scanFiles(MultipartFile[] files);

}
