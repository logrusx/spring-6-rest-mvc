package guru.springframework.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity handleJpaViolations(TransactionSystemException exception) {

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();

        if (exception.getCause().getCause() instanceof ConstraintViolationException cve) {
            List errors = cve.getConstraintViolations()
                    .stream()
                    .map(v -> Map.of(
                            v.getPropertyPath().toString(),
                            v.getMessage()))
                    .toList();
            return bodyBuilder.body(errors);
        }

        return bodyBuilder.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBindErrors(MethodArgumentNotValidException exception) {

        List errorList = exception.getFieldErrors().stream().map(fieldError -> Map.of(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return ResponseEntity.badRequest().body(errorList);
    }


}
