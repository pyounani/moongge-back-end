package com.example.narshaback.base.dto.response;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.code.ResponseCode;
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

    public ResponseDTO(ErrorCode errorCode, T data) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.data = data;
    }

}
