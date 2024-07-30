package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.base.dto.like.DeleteLikeDTO;
import com.narsha.moongge.base.dto.like.LikeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.projection.like.GetLikeList;
import com.narsha.moongge.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // JSON 형태의 결과값 반환
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    /**
     * 좋아요 생성하기 API
     */
    @PostMapping("/groups/{groupCode}/posts/{postId}/likes")
    public ResponseEntity<ResponseDTO> createLike(@PathVariable String groupCode,
                                                  @PathVariable Integer postId,
                                                  @Valid @RequestBody CreateLikeDTO createLikeDTO){
        Integer res = likeService.createLike(groupCode,postId, createLikeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_LIKE, res));
    }

    /**
     * 특정 포스트에 좋아요 누른 유저 목록 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}/likes/users")
    public ResponseEntity<ResponseDTO> getLikeList(@PathVariable String groupCode,
                                                   @PathVariable Integer postId){
        List<LikeDTO> res = likeService.getLikeList(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_LIKE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_LIKE_LIST, res));

    }

    /**
     * 좋아요 취소 API
     */
    @DeleteMapping("/groups/{groupCode}/posts/{postId}/likes")
    public ResponseEntity<ResponseDTO> deleteLike(@PathVariable String groupCode,
                                                  @PathVariable Integer postId,
                                                  @Valid @RequestBody DeleteLikeDTO deleteLikeDTO){
        LikeDTO res = likeService.deleteLike(groupCode, postId, deleteLikeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DELETE_LIKE, res));
    }

    /**
     * 포스트에 좋아요 여부 받아오기 API
     */
    @GetMapping("/groups/{groupCode}/users/{userId}/posts/{postId}/likes/check")
    public ResponseEntity<ResponseDTO> checkLike(@PathVariable String groupCode,
                                                 @PathVariable String userId,
                                                 @PathVariable Integer postId) {
        boolean res = likeService.checkLikePost(userId, groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CHECK_LIKE_POST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CHECK_LIKE_POST, res));
    }

    /**
     * 특정 포스트에 좋아요가 누른 갯수 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}/likes/count")
    public ResponseEntity<ResponseDTO> countLike(@PathVariable String groupCode,
                                                 @PathVariable Integer postId){
        Long res = likeService.countLike(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_COUNT_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_COUNT_LIKE, res));
    }

    @GetMapping("/receive-tenLikes")
    public ResponseEntity<ResponseDTO> checkTenLikes(@RequestParam(value = "userId") String userId, @RequestParam(value="groupCode") String groupCode){

        Boolean isTen = likeService.receiveTenLikes(userId, groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_RECEIVE_TEN_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_RECEIVE_TEN_LIKE, isTen));
    }

    @GetMapping("/give-tenLikes")
    public ResponseEntity<ResponseDTO> giveTenLikes(@RequestParam(value = "userId") String userId){

//        Boolean isTen = likeService.giveTenLikes(userId);
        Long isTen = likeService.giveTenLikes(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GIVE_TEN_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GIVE_TEN_LIKE, isTen));
    }
}
