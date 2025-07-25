package henrotaym.env.http.resources;

import henrotaym.env.enums.PokemonTypeName;
import henrotaym.env.serializers.HasIncludables;
import java.math.BigInteger;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PokemonResource implements HasIncludables {

  private final BigInteger id;
  private final String name;
  // private final String nickname;
  private final Integer attack;
  private final Integer speed;
  private final Integer pv;
  private final Integer defense;
  private final Integer captureRate;
  private final Integer nextEvolutionLevel;
  private final String nameOfNextEvolution;
  // private final Integer level;
  private final PokemonTypeName type;

  // private final PokemonStatusName status;

  @Override
  public Set<String> includables() {
    return null;
    // return Set.of("studio", "cover", "tags");
  }
}
