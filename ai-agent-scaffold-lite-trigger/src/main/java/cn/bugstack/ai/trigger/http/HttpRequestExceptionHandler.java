package cn.bugstack.ai.trigger.http;

import cn.bugstack.ai.api.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpRequestExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Void>> handleUnreadableRequest(HttpMessageNotReadableException exception) {
        Response<Void> response = Response.<Void>builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .info("请求体格式不正确")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
