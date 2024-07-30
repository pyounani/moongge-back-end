package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.projection.like.GetLikeList;
import com.narsha.moongge.service.LikeService;
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
                                                  @RequestBody CreateLikeDTO createLikeDTO){
        Integer res = likeService.createLike(groupCode,postId, createLikeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_LIKE, res));
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
