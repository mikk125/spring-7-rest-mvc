package guru.springframework.spring7restmvc.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException ex) {
        List errorList = ex.getFieldErrors().stream()
                .map(fE -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fE.getField(), fE.getDefaultMessage());

                    return errorMap;
                }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }
}
