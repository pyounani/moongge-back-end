package com.example.narshaback.dto.response;

import com.example.narshaback.api.ResponseCode;
import com.example.narshaback.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private Integer status;

    private String code;
    private String message;
    private T data;

    public ResponseDTO(ResponseCode responseCode, T data) {
        this.status = responseCode.getStatus().value();
        this.code = responseCode.name();
        this.message = responseCode.getMessage();
        this.data = data;
    }
}
