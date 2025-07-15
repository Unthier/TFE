package henrotaym.env.exceptions;

public class UserAlreadyInFightException extends RuntimeException {
  public UserAlreadyInFightException() {
    super("User already has an ongoing fight.");
  }

  public UserAlreadyInFightException(String message) {
    super(message);
  }
}
