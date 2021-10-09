package com.herusantoso.clamav.dto;

import lombok.Data;

@Data
public class FileScanResponseDto {

    private String  fileName;
    private Boolean detected;
    private String  fileType;
    private long    size;
    private long    scanTimeInMilliSec;
    private String  errorMessage;
    private String  hash;
    private long    uploadTime;

}