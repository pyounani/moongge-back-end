package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.notice.NoticeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "NoticeController", description = "공지 관련 API")
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지 작성하기 API
     */
    @PostMapping("/users/{userId}/notices")
    @Operation(
            summary = "공지 작성",
            description = "주어진 그룹 코드에 대해 새로운 공지를 작성하는 API",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "공지 작성 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지 작성에 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "403", description = "학생은 그룹을 생성할 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> createNotice(@PathVariable String userId,
                                                    @Valid @RequestBody CreateNoticeDTO createNoticeDTO) {
        NoticeDTO res = noticeService.createNotice(userId, createNoticeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_NOTICE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_NOTICE, res));
    }

    /**
     * 공지 목록 불러오기 API
     */
    @GetMapping("/users/{userId}/notices")
    @Operation(
            summary = "공지 목록 조회",
            description = "주어진 그룹 코드에 대해 공지 목록을 조회하는 API",
            parameters = @Parameter(name = "userId", description = "공지 목록을 조회할 유저 아이디", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지 목록을 성공적으로 가져왔습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getNoticeList(@PathVariable String userId) {
        List<NoticeDTO> res = noticeService.getNoticeList(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_LIST, res));
    }

    /**
     * 공지 상세사항 내용 불러오기 API
     */
    @GetMapping("/users/{userId}/notices/{noticeId}")
    @Operation(
            summary = "공지 상세 조회",
            description = "주어진 그룹 코드와 공지 ID에 대해 공지의 상세 내용을 조회하는 API",
            parameters = {
                    @Parameter(name = "userId", description = "사용자의 유저 ID", required = true),
                    @Parameter(name = "noticeId", description = "상세 조회할 공지의 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지 상세를 성공적으로 가져왔습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getNoticeDetail(@PathVariable String userId,
                                                       @PathVariable Integer noticeId) {
        NoticeDTO res = noticeService.getNoticeDetail(userId, noticeId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_DETAIL.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_DETAIL, res));
    }

    /**
     * 최근에 올린 공지 한 개 API
     */
    @GetMapping("/users/{userId}/notices/recent")
    @Operation(
            summary = "최근 공지 조회",
            description = "주어진 그룹 코드에 대해 최근에 올라온 공지 하나를 조회하는 API",
            parameters = @Parameter(name = "userId", description = "최근 공지를 조회할 유저 아이디", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "가장 최근 공지를 성공적으로 가져왔습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getRecentNoticeOne(@PathVariable String userId) {
        NoticeDTO res = noticeService.getRecentNoticeOne(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_RECENT_ONE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_RECENT_ONE, res));
    }
}