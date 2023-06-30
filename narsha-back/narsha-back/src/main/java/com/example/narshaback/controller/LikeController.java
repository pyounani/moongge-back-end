package com.example.narshaback.controller;

import com.example.narshaback.base.dto.like.CreateLikeDTO;
import com.example.narshaback.base.projection.like.GetLikeList;
import com.example.narshaback.service.LikeService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
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
    public String createLike(@RequestBody CreateLikeDTO createLikeDTO){
        Integer res = likeService.createLike(createLikeDTO);
        JsonObject obj = new JsonObject();

        obj.addProperty("likeId", res);

        if(res == null) obj.addProperty("message", "좋아요 생성 실패");
        else obj.addProperty("message", "좋아요 생성 성공!");


        return obj.toString();
    }

    @GetMapping("/list")
    public List<GetLikeList> getLikeList(@RequestParam(value="postId") Integer postId, @RequestParam(value="userGroupId") Integer userGroupId){
        List<GetLikeList> list = likeService.getLikeList(postId, userGroupId);

        return list;
    }
}
