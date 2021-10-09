package com.herusantoso.clamav.impl;

import com.herusantoso.clamav.config.ClamAVClient;
import com.herusantoso.clamav.dto.FileScanResponseDto;
import com.herusantoso.clamav.exception.ClamAVSizeLimitException;
import com.herusantoso.clamav.service.FileScanService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileScanServiceImpl implements FileScanService {

    private static Logger LOGGER = LoggerFactory.getLogger(FileScanServiceImpl.class);

    @Autowired
    private ClamAVClient clamAVClient;

    public List<FileScanResponseDto> scanFiles(MultipartFile[] files) {
        LOGGER.info("File received = {} ", (files != null ? files.length : null));
        return Arrays.stream(files).map(multipartFile -> {
            FileScanResponseDto dto = new FileScanResponseDto();
            long startTime = System.currentTimeMillis();
            dto.setUploadTime(startTime);
            try {
                byte[] response = clamAVClient.scan(multipartFile.getInputStream(), dto);
                Boolean status = ClamAVClient.isCleanReply(response);
                dto.setDetected(status != null ? !status : status);
                LOGGER.info("File Scanned = {} Clam AV Response = {} ", multipartFile.getOriginalFilename(),
                        (status != null ? status : null));
            } catch (ClamAVSizeLimitException exception) {
                LOGGER.error("Exception occurred while scanning using clam av = {} ", exception.getMessage());
                dto.setErrorMessage(exception.getMessage());
            } catch (Exception e) {
                LOGGER.error("Exception occurred while scanning using clam av = {} ", e.getMessage());
                dto.setErrorMessage(e.getMessage());
            }
            dto.setFileName(multipartFile.getOriginalFilename());
            dto.setFileType(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            dto.setSize(multipartFile.getSize());
            dto.setScanTimeInMilliSec(System.currentTimeMillis() - startTime);
            return dto;
        }).collect(Collectors.toList());
    }
}
