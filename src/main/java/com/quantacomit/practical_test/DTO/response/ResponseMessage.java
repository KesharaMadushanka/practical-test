package com.quantacomit.practical_test.DTO.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResponseMessage {
    private long statusCode;
    private String message;
    private Object data;


    public ResponseMessage(long statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(long statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
