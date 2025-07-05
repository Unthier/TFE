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
public class TrainerResource implements HasIncludables {
  private final BigInteger id;
  private final String name;

  @Override
  public Set<String> includables() {
    return null;
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'includables'");
  }
}
