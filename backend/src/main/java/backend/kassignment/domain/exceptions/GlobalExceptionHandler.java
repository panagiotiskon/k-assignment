package backend.kassignment.domain.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Void> handleProductNotFoundException(Exception ex) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Void> handleUserNotFoundException(Exception ex) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(value = {PurchaseNotFoundException.class})
    public ResponseEntity<Void> handleOrderNotFoundException(Exception ex) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(value = {PurchaseItemNotFoundException.class})
    public ResponseEntity<Void> handleOrderItemNotFoundException(Exception ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            System.out.println(error.getField());
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errorMap);
    }

}
