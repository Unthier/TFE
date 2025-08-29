package henrotaym.env.http.resources;

import java.math.BigInteger;
import java.util.Set;

import henrotaym.env.enums.MethodeName;
import henrotaym.env.serializers.HasIncludables;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CommandResource implements HasIncludables {
  private final BigInteger id;
  private final MethodeName methode;
  private final String url;
  private final String descript;

  @Override
  public Set<String> includables() {
    return null;
    // return Set.of("user");
  }
}
