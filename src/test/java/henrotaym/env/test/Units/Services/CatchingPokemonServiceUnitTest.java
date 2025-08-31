package henrotaym.env.test.Units.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import henrotaym.env.ApplicationTest;
import henrotaym.env.Factories.PokemonCatchingFactory;
import henrotaym.env.entities.Pokemon;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.User;
import henrotaym.env.http.resources.UserResource;
import henrotaym.env.repositories.PokemonCatchingRepository;
import henrotaym.env.repositories.UserRepository;
import henrotaym.env.services.PokemonCatchingService;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CatchingPokemonServiceUnitTest extends ApplicationTest {
  @Test
  public void it_should_add_a_pokemon_catching_to_the_user() {

    BigInteger userId = BigInteger.ONE;
    User user = mock(User.class);

    PokemonCatchingFactory pokemonCatchingFactory = new PokemonCatchingFactory();
    PokemonCatchingRepository pokemonCatchingRepository = mock(PokemonCatchingRepository.class);
    PokemonCatching pokemonCatching = mock(PokemonCatching.class);
    UserRepository userRepository = mock(UserRepository.class);

    user.setId(userId);
    user.setPokemonsCatchings(new ArrayList<>());

    pokemonCatching.setCatchingOn(LocalDateTime.now());

    PokemonCatchingService pokemonCatchingService =
        new PokemonCatchingService(
            pokemonCatchingRepository, null, null, null, pokemonCatchingFactory, userRepository);

    when(pokemonCatchingFactory.create(any(Pokemon.class))).thenReturn(pokemonCatching);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
    UserResource result = pokemonCatchingService.catchPokemon(userId);

    assertEquals(1, user.getPokemonsCatchings().size());
    assertEquals(user, pokemonCatching.getUser());
    assertEquals(user.getId(), result.getId());

    verify(userRepository).save(user);
  }
}
