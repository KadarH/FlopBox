
package com.nouria.flopBox.exceptions.advices;

import com.nouria.flopBox.exceptions.FtpServerNotConnectedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FtpServerNotConnectedAdvice {

    @ResponseBody
    @ExceptionHandler(FtpServerNotConnectedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ftpServerNotFoundHandler(FtpServerNotConnectedException ex) {
        return ex.getMessage();
    }
}
