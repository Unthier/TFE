package henrotaym.env.scheduler;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.emitters.Emitter;
import henrotaym.env.queues.events.PokemonGenerateByIAEvent;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
@Profile(ProfileName.SCHEDULER)
public class PokemonGenerateByIAJob {
  private Emitter emitter;

  @Scheduled(timeUnit = TimeUnit.DAYS, fixedDelay = 7) // every week
  public void handle() {
    PokemonGenerateByIAEvent createdEvent =
        new PokemonGenerateByIAEvent("pokemon generate by IA event triggered");
    this.emitter.send(createdEvent);
    log.info("pokemon generation by IA job executed, event sent: {}", createdEvent.getMessage());
  }
}
