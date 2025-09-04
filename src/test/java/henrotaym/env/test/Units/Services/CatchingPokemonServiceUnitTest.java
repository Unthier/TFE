package henrotaym.env.test.Units.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import henrotaym.env.ApplicationTest;
import henrotaym.env.Factories.PokemonCatchingFactory;
import henrotaym.env.Factories.PokemonTrainingFactory;
import henrotaym.env.entities.Pokemon;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.User;
import henrotaym.env.http.resources.UserResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.PokemonCatchingRepository;
import henrotaym.env.repositories.PokemonRepository;
import henrotaym.env.repositories.UserRepository;
import henrotaym.env.services.PokemonCatchingService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CatchingPokemonServiceUnitTest extends ApplicationTest {
  @Test
  public void it_should_add_a_pokemon_catching_to_the_user() {

    BigInteger userId = BigInteger.ONE;
    User user = new User();
    user.setId(userId);
    user.setPokemonsCatchings(new ArrayList<>());

    // Mocks
    PokemonCatchingFactory pokemonCatchingFactory = mock(PokemonCatchingFactory.class);
    PokemonCatchingRepository pokemonCatchingRepository = mock(PokemonCatchingRepository.class);
    PokemonRepository pokemonRepository = mock(PokemonRepository.class);
    PokemonCatching pokemonCatching = new PokemonCatching();
    UserRepository userRepository = mock(UserRepository.class);
    ResourceMapper resourceMapper = mock(ResourceMapper.class);
    PokemonTrainingFactory pokemonTrainingFactory = mock(PokemonTrainingFactory.class); // ✅ corrigé

    // Création d'un Pokémon réel
    Pokemon pokemon = new Pokemon();
    pokemon.setId(BigInteger.valueOf(1));
    pokemon.setName("Pikachu");
    pokemon.setAttack(50);

    // Création d'un UserResource fictif pour le retour du mapper
    UserResource userResource = new UserResource(userId, null, null, null);

    // Service avec tous les mocks
    PokemonCatchingService pokemonCatchingService =
        new PokemonCatchingService(
            pokemonCatchingRepository,
            resourceMapper,
            pokemonTrainingFactory,
            pokemonRepository,
            pokemonCatchingFactory,
            userRepository);

    // Stubs
    when(pokemonRepository.findAllIds()).thenReturn(List.of(BigInteger.valueOf(1)));
    when(pokemonRepository.findById(BigInteger.valueOf(1))).thenReturn(Optional.of(pokemon));
    when(pokemonCatchingFactory.create(any(Pokemon.class))).thenReturn(pokemonCatching);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
    when(resourceMapper.userResource(user)).thenReturn(userResource); // ✅ Ajouté

    // Act
    UserResource result = pokemonCatchingService.catchPokemon(userId);

    // Assert
    assertEquals(1, user.getPokemonsCatchings().size());
    assertEquals(user, pokemonCatching.getUser());
    assertEquals(user.getId(), result.getId());

    verify(userRepository).save(user);
    verify(pokemonRepository).findAllIds();
    verify(pokemonRepository).findById(BigInteger.valueOf(1));
    verify(pokemonCatchingFactory).create(pokemon);
    verify(resourceMapper).userResource(user); // ✅ On vérifie aussi l'appel
  }
}
