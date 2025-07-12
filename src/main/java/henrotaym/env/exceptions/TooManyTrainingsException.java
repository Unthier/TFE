package henrotaym.env.exceptions;

public class TooManyTrainingsException extends RuntimeException {
  public TooManyTrainingsException(String message) {
    super(message);
  }

  public TooManyTrainingsException(String message, Throwable cause) {
    super(message, cause);
  }

  public TooManyTrainingsException(Throwable cause) {
    super(cause);
  }

  public TooManyTrainingsException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
