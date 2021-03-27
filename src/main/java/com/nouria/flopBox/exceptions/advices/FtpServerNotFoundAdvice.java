
package com.nouria.flopBox.exceptions.advices;

import com.nouria.flopBox.exceptions.FtpServerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FtpServerNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FtpServerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ftpServerNotFoundHandler(FtpServerNotFoundException ex) {
        return ex.getMessage();
    }
}
