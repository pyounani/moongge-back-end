package com.example.narshaback.controller;

import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/ai-flask")
public class AIController {
    @PostMapping("/object-detect")
    public ResponseEntity<ResponseDTO> objectDetect(@RequestParam("images") List<MultipartFile> imageFiles) throws Exception {
        // request setting //
        RestTemplate restTemplate = new RestTemplate(); // req set
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>(); // body set
        List<String> fileNameArr = new ArrayList<>();
        List<String> ImageArr = new ArrayList<>();

        // 1. Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 2. Body set
        for (MultipartFile image : imageFiles){
            String fileName = image.getOriginalFilename();
            String imageFileString = getBase64String(image);
            fileNameArr.add(fileName);
            ImageArr.add(imageFileString);
        }

        //System.out.print(fileNameArr.toString());

        body.add("filename", fileNameArr.toString());
        body.add("images", ImageArr.toString());
        System.out.println(ImageArr.size());



        // 3. Message
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
        //System.out.println(requestMessage);
        // 4. Request
        //HttpEntity<String> request = restTemplate.postForEntity("http://127.0.0.1:8000/image/object-detect/narsha_yolo5_model_ysy6", requestMessage, String.class);

        // 5. Result
        String res = restTemplate.postForObject("http://127.0.0.1:8000/image/object-detect/narsha_yolo5_model_ysy", requestMessage, String.class);
        System.out.println(res);
        // send to flask, get result

        JSONParser parser = new JSONParser(res);
        //JsonObject jsonobject = (JsonObject) parser.parse();



        // return masking image, xxyy(use to front when display masking item list)
        return ResponseEntity
                .status(ResponseCode.SUCCESS_IMAGE_MASKING.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_IMAGE_MASKING, res));
    }

    private String getBase64String(MultipartFile multipartFile) throws Exception {
        byte[] bytes = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
