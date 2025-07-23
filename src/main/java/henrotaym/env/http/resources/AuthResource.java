package henrotaym.env.http.resources;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class AuthResource {
  private final String token;
}
