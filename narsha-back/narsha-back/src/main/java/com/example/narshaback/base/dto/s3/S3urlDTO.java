package com.example.narshaback.base.dto.s3;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class S3urlDTO {
    private String uploadFilePath;
    private String uploadFileName;

    public S3urlDTO(String uploadFilePath, String uploadFileName){
        this.uploadFileName = uploadFileName;
        this.uploadFilePath = uploadFilePath;
    }

}
