package com.example.narshaback.base.dto.s3;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class S3PathDTO {

    private Integer postId;
    private List<S3urlDTO> dto;

    public S3PathDTO(Integer postId, List<S3urlDTO> dto){
        this.dto = dto;
        this.postId = postId;
    }
}
