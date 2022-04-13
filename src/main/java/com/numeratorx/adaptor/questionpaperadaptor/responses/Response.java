package com.numeratorx.adaptor.questionpaperadaptor.responses;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Response {
    private LocalDateTime timestamp;
    private String responseMessage;
    private StatusCode responseStatus;
    private Map<String, Object> data;

    public Response(LocalDateTime timestamp, String message, StatusCode status, Map<String, Object> data) {
        this.timestamp = timestamp;
        this.responseMessage = message;
        this.responseStatus = status;
        this.data = data;
    }
}
