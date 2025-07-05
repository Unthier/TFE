package henrotaym.env.http.resources;

import henrotaym.env.serializers.HasIncludables;
import java.math.BigInteger;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class UserResource implements HasIncludables {

  private final BigInteger id;
  private final String name;
  private final Integer catching_number;

  @Override
  public Set<String> includables() {
    return null;
    // throw new UnsupportedOperationException("Unimplemented method 'includables'");
  }
}
