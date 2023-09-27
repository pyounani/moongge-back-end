package com.example.narshaback.controller;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.like.CreateLikeDTO;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.example.narshaback.base.exception.GroupCodeNotFoundException;
import com.example.narshaback.base.exception.PostNotFoundException;

import com.example.narshaback.base.projection.like.GetLikeList;
import com.example.narshaback.service.LikeService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLike(@RequestBody CreateLikeDTO createLikeDTO){
        Integer likeId = likeService.createLike(createLikeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_LIKE, likeId));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getLikeList(@RequestParam(value="postId") Integer postId){
        List<GetLikeList> likeList = likeService.getLikeList(postId);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_LIKE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_LIKE_LIST, likeList));

    }

    @GetMapping("/check")
    public ResponseEntity<ResponseDTO> checkLike(@RequestParam(value="userId") String userId, @RequestParam(value="groupCode") String groupCode,
                                                 @RequestParam(value="postId") Integer postId){
        boolean checkLike = likeService.checkLikePost(userId, groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CHECK_LIKE_POST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CHECK_LIKE_POST, checkLike));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLike(@RequestParam(value="userId") String userId, @RequestParam(value="groupCode") String groupCode,
                                                  @RequestParam(value="postId") Integer postId){
        String deleteLike = likeService.deleteLike(userId, groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DELETE_LIKE, deleteLike));
    }

    @GetMapping("/count")
    public ResponseEntity<ResponseDTO> countLike(@RequestParam(value="groupCode") String groupCode, @RequestParam(value="postId") Integer postId){

        Long like = likeService.countLike(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_COUNT_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_COUNT_LIKE, like));
    }

    @GetMapping("/check-tenLikes")
    public ResponseEntity<ResponseDTO> checkTenLikes(@RequestParam(value = "userId") String userId, @RequestParam(value="groupCode") String groupCode){

        Boolean isTen = likeService.checkTenLikes(userId, groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_COUNT_TEN_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_COUNT_TEN_LIKE, isTen));
    }
}
