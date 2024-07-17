package com.narsha.moongge.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface AmazonS3Service {

    String uploadFileToS3(MultipartFile multipartFile, String filePath);

    String putS3(File uploadFile, String fileName);

    void deleteS3(String filePath);

    List<String> getAllPhotos(String folderPath);
}
