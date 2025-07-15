package henrotaym.env.exceptions;

import henrotaym.env.http.resources.exceptions.ApiExceptionResource;
import henrotaym.env.mappers.ApiExceptionMapper;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
  private ApiExceptionMapper apiExceptionMapper;

  @ExceptionHandler(exception = EntityNotFoundException.class)
  public ResponseEntity<ApiExceptionResource> handleEntityNotFoundException(
      EntityNotFoundException exception) {
    ApiException apiException = this.apiExceptionMapper.entityNotFound(exception);

    return this.apiExceptionMapper.responseEntity(apiException);
  }

  @ExceptionHandler(exception = MethodArgumentNotValidException.class)
  public ResponseEntity<ApiExceptionResource> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    ApiException apiException = this.apiExceptionMapper.methodArgumentNotValid(exception);

    return this.apiExceptionMapper.responseEntity(apiException);
  }

  @ExceptionHandler(exception = MaxNumberCatchingException.class)
  public ResponseEntity<ApiExceptionResource> handleMaxDailyCatchingReachedException(
      MaxNumberCatchingException exception) {

    ApiException apiException =
        new ApiException(
            exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now(), null, null, null);
    return this.apiExceptionMapper.responseEntity(apiException);
  }

  @ExceptionHandler(exception = TooManyTrainingsException.class)
  public ResponseEntity<ApiExceptionResource> handleTooManyTrainingsException(
      TooManyTrainingsException exception) {

    ApiException apiException =
        new ApiException(
            exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now(), null, null, null);
    return this.apiExceptionMapper.responseEntity(apiException);
  }

  @ExceptionHandler(exception = UserAlreadyInFightException.class)
  public ResponseEntity<ApiExceptionResource> handleUserAlreadyInFightException(
      UserAlreadyInFightException exception) {
    ApiException apiException =
        new ApiException(
            exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now(), null, null, null);
    return this.apiExceptionMapper.responseEntity(apiException);
  }
}
