package henrotaym.env.http.resources;

import henrotaym.env.enums.TrainerStatusName;
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
public class TrainerResource implements HasIncludables {
  private final BigInteger id;
  private final String name;
  private final TrainerStatusName status;
  private List<PokemonTrainerResource> pokemons;

  @Override
  public Set<String> includables() {
    return Set.of("pokemons");
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'includables'");
  }
}
