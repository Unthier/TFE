package henrotaym.env.mappers;

import henrotaym.env.entities.Pokemon;
import henrotaym.env.http.requests.PokemonRequest;
import henrotaym.env.http.resources.PokemonResource;
import org.springframework.stereotype.Component;

@Component
public class PokemonMapper {
  public PokemonResource resource(Pokemon pokemon) {
    return new PokemonResource(
        pokemon.getId(),
        pokemon.getName(),
        pokemon.getAttack(),
        pokemon.getSpeed(),
        pokemon.getPv(),
        pokemon.getDefense(),
        pokemon.getCaptureRate(),
        pokemon.getNextEvolutionLevel(),
        pokemon.getNameOfNextEvolution(),
        pokemon.getType());
  }

  public Pokemon request(PokemonRequest request, Pokemon pokemon) {
    pokemon.setName(request.name());
    pokemon.setAttack(request.attack());
    pokemon.setDefense(request.defense());
    pokemon.setPv(request.pv());
    pokemon.setSpeed(request.speed());
    pokemon.setType(request.type());
    pokemon.setCaptureRate(request.captureRate());
    pokemon.setNextEvolutionLevel(request.nextEvolutionLevel());
    pokemon.setNameOfNextEvolution(request.nameOfNextEvolution());

    return pokemon;
  }
}
