package henrotaym.env.http.resources;

import henrotaym.env.enums.PokemonCatchingStatusName;
import henrotaym.env.enums.PokemonTypeName;
import henrotaym.env.serializers.HasIncludables;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PokemonCatchingResource implements HasIncludables {

  private final BigInteger id;
  private final BigInteger pokemonId;
  private final String name;
  private final String nickname;
  private final Integer attack;
  private final Integer speed;
  private final Integer pv;
  private final Integer defense;
  private final Integer level;
  private final String nameOfNextEvolution;
  private final Integer nextEvolutionLevel;
  private final PokemonTypeName type;
  private final LocalDateTime catchingOn;
  private final PokemonCatchingStatusName status;
  private final Integer damage;
  private UserResource user;
  private List<PokemonTrainingResource> trainings;

  @Override
  public Set<String> includables() {
    return Set.of("user", "trainings");
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'includables'");
  }
}
