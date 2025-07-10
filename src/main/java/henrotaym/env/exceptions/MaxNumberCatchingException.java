package henrotaym.env.exceptions;

public class MaxNumberCatchingException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public MaxNumberCatchingException() {
    super("You have reached the maximum number of Pokémon catchings allowed.");
  }

  public MaxNumberCatchingException(String message) {
    super(message);
  }

  public MaxNumberCatchingException(String message, Throwable cause) {
    super(message, cause);
  }
}
