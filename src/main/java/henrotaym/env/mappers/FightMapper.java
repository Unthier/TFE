package henrotaym.env.mappers;

import henrotaym.env.entities.Fight;
import henrotaym.env.http.requests.relationships.FightRelationshipRequest;
import henrotaym.env.http.resources.FightResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FightMapper {
  public FightResource resource(Fight fight) {
    return new FightResource(
        fight.getId(),
        fight.getPokemonTrainerId(),
        fight.getPokemonCatchingId(),
        fight.getPokemonTrainerDamage(),
        fight.getPokemonCatchingDamage(),
        fight.getTours(),
        fight.getState());
  }

  public FightRelationshipRequest relationshipRequest(Fight fight) {
    return new FightRelationshipRequest(fight.getId());
  }
}
