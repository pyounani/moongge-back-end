package com.narsha.moongge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@Tag(name = "HealthCheckController", description = "HealthCheckController API")
@RestController
public class HealthCheckController {

    @Value("${server.env}")
    private String env;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.serverAddress}")
    private String serverAddress;
    @Value("${serverName}")
    private String serverName;

    /**
     * 서버 상태 확인 API
     */
    @GetMapping("/hc")
    @Operation(
            summary = "서버 상태 확인",
            description = "서버의 현재 상태 및 환경 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "서버 상태 확인 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<?> healthCheck() {
        Map<String, String> responseData = new TreeMap<>();
        responseData.put("serverName", serverName);
        responseData.put("serverAddress", serverAddress);
        responseData.put("serverPort", serverPort);
        responseData.put("env", env);

        return ResponseEntity.ok(responseData);
    }

    /**
     * 서버 환경 변수 조회 API
     */
    @GetMapping("/env")
    @Operation(
            summary = "서버 환경 변수 조회",
            description = "서버의 환경 변수를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "서버 환경 변수 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<?> getEnv() {
        return ResponseEntity.ok(env);
    }

}
