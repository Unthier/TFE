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
        pokemon.getLevel(),
        pokemon.getType() /*,
        pokemon.getStatus()*/);
  }

  public Pokemon request(PokemonRequest request, Pokemon pokemon) {
    pokemon.setName(request.name());
    pokemon.setNickname(request.nickname());
    pokemon.setAttack(request.attack());
    pokemon.setDefense(request.defense());
    pokemon.setLevel(request.level());
    pokemon.setPv(request.pv());
    pokemon.setSpeed(request.speed());
    pokemon.setType(request.type());
    pokemon.setStatus(request.status());

    return pokemon;
  }
}
