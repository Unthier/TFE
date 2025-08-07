package henrotaym.env.test.features.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.PokemonCatchingFactoryTest;
import henrotaym.env.database.factories.UserFactoryTest;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.User;
import henrotaym.env.repositories.PokemonCatchingRepository;
import henrotaym.env.repositories.UserRepository;
import henrotaym.env.services.AuthSercive;
import henrotaym.env.utils.api.JsonClient;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserControllerFeatureTest extends ApplicationTest {
  @Autowired ObjectMapper objectMapper;

  @Autowired UserFactoryTest userFactory;

  @Autowired PokemonCatchingFactoryTest pokemonCatchingFactory;

  @Autowired PokemonCatchingRepository pokemonCatchingRepository;

  @Autowired UserRepository userRepository;

  @Autowired JsonClient jsonClient;

  @Autowired AuthSercive authSercive;

  @Test
  public void it_responds_to_catch_a_pokemon_under_the_limte_by_day() throws Exception {
    User user = this.userFactory.create();

    Faker faker = new Faker();

    PokemonCatchingFactoryTest pokemonCatchingFactoryTest =
        new PokemonCatchingFactoryTest(faker, pokemonCatchingRepository, userFactory);

    PokemonCatching pokemonCatching1 = pokemonCatchingFactoryTest.create();
    PokemonCatching pokemonCatching2 = pokemonCatchingFactoryTest.create();
    PokemonCatching pokemonCatching3 = pokemonCatchingFactoryTest.create();
    PokemonCatching pokemonCatching4 = pokemonCatchingFactoryTest.create();
    PokemonCatching pokemonCatching5 = pokemonCatchingFactoryTest.create();

    user.getPokemonsCatchings().add(pokemonCatching1);
    user.getPokemonsCatchings().add(pokemonCatching2);
    user.getPokemonsCatchings().add(pokemonCatching3);
    user.getPokemonsCatchings().add(pokemonCatching4);
    user.getPokemonsCatchings().add(pokemonCatching5);

    String token = this.authSercive.generateToken(user.getName());

    Integer pokemonNumber = user.getPokemonsCatchings().size();

    this.jsonClient
        .request(request -> request.post("/users/{userId}/catch", user.getId()))
        // .header("Authorization", "Bearer " + token))
        .perform()
        .status(status -> status.isBadRequest());

    User newUser = this.userRepository.findById(user.getId()).get();
    assertEquals(pokemonNumber, newUser.getPokemonsCatchings().size());
    assertEquals(user.getId(), newUser.getId());
  }
}
