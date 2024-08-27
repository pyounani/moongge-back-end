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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
@Tag(name = "NoticeController", description = "공지 관련 API")
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지 작성하기 API
     */
    @PostMapping("/{groupCode}")
    @Operation(
            summary = "공지 작성",
            description = "주어진 그룹 코드에 대해 새로운 공지를 작성하는 API",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "공지 작성 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지 작성 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> createNotice(@Valid @RequestBody CreateNoticeDTO createNoticeDTO) {
        NoticeDTO res = noticeService.createNotice(createNoticeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_NOTICE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_NOTICE, res));
    }

    /**
     * 공지 목록 불러오기 API
     */
    @GetMapping("/{groupCode}")
    @Operation(
            summary = "공지 목록 조회",
            description = "주어진 그룹 코드에 대해 공지 목록을 조회하는 API",
            parameters = @Parameter(name = "groupCode", description = "공지 목록을 조회할 그룹 코드", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지 목록 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getNoticeList(@PathVariable String groupCode) {
        List<NoticeDTO> res = noticeService.getNoticeList(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_LIST, res));
    }

    /**
     * 공지 상세사항 내용 불러오기 API
     */
    @GetMapping("/{groupCode}/{noticeId}")
    @Operation(
            summary = "공지 상세 조회",
            description = "주어진 그룹 코드와 공지 ID에 대해 공지의 상세 내용을 조회하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "공지가 포함된 그룹의 코드", required = true),
                    @Parameter(name = "noticeId", description = "상세 조회할 공지의 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지 상세 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "공지 또는 그룹을 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getNoticeDetail(@PathVariable String groupCode,
                                                       @PathVariable Integer noticeId) {
        NoticeDTO res = noticeService.getNoticeDetail(groupCode, noticeId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_DETAIL.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_DETAIL, res));
    }

    /**
     * 최근에 올린 공지 한 개 API
     */
    @GetMapping("/{groupCode}/recent")
    @Operation(
            summary = "최근 공지 조회",
            description = "주어진 그룹 코드에 대해 최근에 올라온 공지 하나를 조회하는 API",
            parameters = @Parameter(name = "groupCode", description = "최근 공지를 조회할 그룹 코드", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "최근 공지 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getRecentNoticeOne(@PathVariable String groupCode) {
        NoticeDTO res = noticeService.getRecentNoticeOne(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_RECENT_ONE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_RECENT_ONE, res));
    }
}