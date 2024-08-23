package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.TextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/ai-flask")
@Tag(name = "AIController", description = "AIController API")
public class AIController {
    private final TextService textService;

//    @PostMapping("/image-mask")
//    public ResponseEntity<ResponseDTO> imageMask(@RequestParam("images") List<MultipartFile> imageFiles) throws Exception {
//        // request setting //
//        RestTemplate restTemplate = new RestTemplate(); // req set
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>(); // body set
//
//        List<String> fileNameArr = new ArrayList<>();
//        List<String> ImageArr = new ArrayList<>();
//
//        // 1. Header set
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        // 2. Body set
//        for (MultipartFile image : imageFiles){
//            String fileName = image.getOriginalFilename();
//            String imageFileString = getBase64String(image);
//            fileNameArr.add(fileName);
//            ImageArr.add(imageFileString);
//        }
//
//        body.add("filename", fileNameArr.toString());
//        body.add("images", ImageArr.toString());
//
//        // 3. Message
//        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
//        System.out.println(requestMessage);
//
//        // 4. Request
//        String res = restTemplate.postForObject("http://127.0.0.1:8000/image/mask-image", requestMessage, String.class);
//        System.out.println(res);
//
//        // 5. Result
//        //JSONParser parser = new JSONParser(res);
//
//        return ResponseEntity
//                .status(ResponseCode.SUCCESS_IMAGE_MASKING.getStatus().value())
//                .body(new ResponseDTO(ResponseCode.SUCCESS_IMAGE_MASKING, res));
//    }


    /**
     * 객체 감지 API
     */
    @PostMapping("/object-detect")
    @Operation(
            summary = "객체 감지",
            description = "여러 이미지를 업로드하여 객체를 감지하는 API입니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "객체 감지할 이미지 파일들",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "객체 감지 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
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

        System.out.println(imageFiles);

        //System.out.print(fileNameArr.toString());

        body.add("filename", fileNameArr.toString());
        body.add("images", ImageArr.toString());
        System.out.println(ImageArr.size());



        // 3. Message
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
        System.out.println(requestMessage);
        //System.out.println(requestMessage);
        // 4. Request
        //HttpEntity<String> request = restTemplate.postForEntity("http://127.0.0.1:8000/image/object-detect/narsha_yolo5_model_ysy6", requestMessage, String.class);

        // 5. Result
        String res = restTemplate.postForObject("http://127.0.0.1:8000/image/object-detect/nersha_yolo5_model_ysy", requestMessage, String.class);
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

    /**
     * 텍스트 필터링 API
     */
    @GetMapping("/text-filter")
    @Operation(
            summary = "텍스트 필터링",
            description = "주어진 텍스트에서 필터링된 내용을 반환하는 API입니다.",
            parameters = @Parameter(name = "text", description = "필터링할 텍스트", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "텍스트 필터링 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<ResponseDTO> textFilter(@RequestParam("text") String text){

        String res = textService.getTextFiltering(text);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_TEXT_FILTERING.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_TEXT_FILTERING, res));
    }
}
