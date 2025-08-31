package henrotaym.env.queues.listeners;

import henrotaym.env.Factories.PokemonCatchingFactory;
import henrotaym.env.annotations.KafkaRetryableListener;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.User;
import henrotaym.env.enums.PokemonCatchingStatusName;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.events.EggHatchEvent;
import henrotaym.env.repositories.PokemonCatchingRepository;
import henrotaym.env.repositories.PokemonRepository;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
@Profile(ProfileName.QUEUE)
public class EggHatchListener implements Listener<EggHatchEvent> {

  private final PokemonCatchingRepository pokemonCatchingRepository;
  private final PokemonRepository pokemonRepository;
  private final PokemonCatchingFactory pokemonCatchingFactory;

  @Override
  @KafkaRetryableListener(EggHatchEvent.EVENT_NAME)
  public void listen(EggHatchEvent event) {
    log.info("Egg hatch event received: {}", event.getMessage());
    List<PokemonCatching> pokemonCatchings =
        this.pokemonCatchingRepository.findAll().stream()
            .filter(
                pokemonCatching ->
                    pokemonCatching.getStatus().equals(PokemonCatchingStatusName.EGG))
            .toList();
    List<BigInteger> pokemonIds = this.pokemonRepository.findAllIds();
    log.info(pokemonCatchings.size() + " Pokémon catchings found in egg status");
    for (PokemonCatching pokemonCatching : pokemonCatchings) {
      if (pokemonCatching.getCatchingOn().isBefore(LocalDateTime.now().minusHours(6))) {
        User user = pokemonCatching.getUser();
        BigInteger pokemonCatchingId = pokemonCatching.getId();
        pokemonCatching =
            this.pokemonCatchingFactory.create(
                this.pokemonRepository
                    .findById(pokemonIds.get(new Random().nextInt(pokemonIds.size())))
                    .get());
        pokemonCatching.setUser(user);
        pokemonCatching.setId(pokemonCatchingId);
        this.pokemonCatchingRepository.save(pokemonCatching);
        log.info("New Pokémon catching hatched");
      }
    }
  }
}
