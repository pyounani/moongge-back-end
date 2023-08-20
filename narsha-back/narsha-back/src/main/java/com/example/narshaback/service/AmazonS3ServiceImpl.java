package com.example.narshaback.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.narshaback.base.dto.s3.S3FileDTO;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class AmazonS3ServiceImpl implements AmazonS3Service{

    @Value("${spring.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;

    private final UserRepository userRepository;

    @Override
    public List<S3FileDTO> uploadFiles(String fileType, List<MultipartFile> multipartFiles) {
        List<S3FileDTO> s3files = new ArrayList<>();

        String uploadFilePath = fileType + "/" + getFolderName();

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {

                String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자

                // S3에 폴더 및 파일 업로드
                amazonS3Client.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));

        /*amazonS3Client.putObject(
            new PutObjectRequest(bucket, s3Key, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));*/

                // S3에 업로드한 폴더 및 파일 URL
                uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();

            } catch (IOException e) {
                e.printStackTrace();
                log.error("Filed upload failed", e);
            }

            s3files.add(
                    S3FileDTO.builder()
                            .originalFileName(originalFileName)
                            .uploadFileName(uploadFileName)
                            .uploadFilePath(uploadFilePath)
                            .uploadFileUrl(uploadFileUrl)
                            .build());
        }

        return s3files;
    }

    @Override
    public S3FileDTO uploadFile(MultipartFile multipartFile) {

        String uploadFilePath = "profile" + "/" + getFolderName();


        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);
        String uploadFileUrl = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {

            String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자

            // S3에 폴더 및 파일 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

    /*amazonS3Client.putObject(
        new PutObjectRequest(bucket, s3Key, inputStream, objectMetadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));*/

            // S3에 업로드한 폴더 및 파일 URL
            uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Filed upload failed", e);
        }

        S3FileDTO s3files = S3FileDTO.builder()
                        .originalFileName(originalFileName)
                        .uploadFileName(uploadFileName)
                        .uploadFilePath(uploadFilePath)
                        .uploadFileUrl(uploadFileUrl)
                        .build();


        return s3files;
    }

    @Override
    public String deleteFile(String userId) {
        String result = "success";

        Optional<UserEntity> findUser = userRepository.findByUserId(userId);
        String uploadFilePath = findUser.get().getProfileImage();

        // split
        Pattern pattern = Pattern.compile("com/");
        String[] list = pattern.split(uploadFilePath);

        if(uploadFilePath != "") {
            try {
                // s3에 저장된 파일이름 대신 url로 가져오는 방법 찾기
                boolean isObjectExist = amazonS3Client.doesObjectExist(bucketName, list[list.length-1]);
                if (isObjectExist) {
                    amazonS3Client.deleteObject(bucketName, uploadFilePath);
                } else {
                    result = "file not found";
                }
            } catch (Exception e) {
                log.debug("Delete File failed", e);
            }

        } else{
            result = "Can not find profile url in DB.";
        }

        return result;
    }

    @Override
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }

    @Override
    public String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", "/");
    }
}
