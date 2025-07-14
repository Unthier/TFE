package henrotaym.env.http.resources;

import henrotaym.env.enums.FightStateName;
import henrotaym.env.serializers.HasIncludables;
import java.math.BigInteger;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class FightResource implements HasIncludables {

  private final BigInteger id;
  private final BigInteger pokemonTrinerId;
  private final BigInteger pokemonCatchingId;
  private final Integer trainerDamage;
  private final Integer catchingDamage;
  private final Integer tours;
  private final FightStateName state;
  private UserResource user;

  @Override
  public Set<String> includables() {
    return Set.of("user");
  }
}
