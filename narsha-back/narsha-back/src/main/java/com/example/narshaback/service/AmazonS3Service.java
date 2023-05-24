package com.example.narshaback.service;

import com.example.narshaback.dto.s3.S3FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AmazonS3Service {
    List<S3FileDTO> uploadFiles(String fileType, List<MultipartFile> multipartFiles); // n개의 파일 업로드

    S3FileDTO uploadFile(MultipartFile multipartFile); // 1개의 파일 업로드

    String deleteFile(String uploadFilePath, String uuidFileName); // 파일 삭제
    String getUuidFileName(String fileName); // uuid 파일명 변환
    String getFolderName(); // 년월일 폴더명 변환
}
