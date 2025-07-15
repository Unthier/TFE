package henrotaym.env.http.resources;

import henrotaym.env.enums.UserRoleName;
import henrotaym.env.serializers.HasIncludables;
import java.math.BigInteger;
import java.util.List;
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
  private final UserRoleName role;
  // private final Integer catching_number;
  private final String mail;
  private List<PokemonCatchingResource> pokemonsCatchings;
  private List<FightResource> fights;

  @Override
  public Set<String> includables() {
    return Set.of("pokemonsCatching", "fights");
    // throw new UnsupportedOperationException("Unimplemented method 'includables'");
  }
}
