package henrotaym.env.scheduler;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.emitters.Emitter;
import henrotaym.env.queues.events.EggHatchEvent;
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
public class EggHatchJob {
  private Emitter emitter;

  @Scheduled(timeUnit = TimeUnit.HOURS, fixedDelay = 1)
  public void handle() {
    EggHatchEvent createdEvent = new EggHatchEvent("egg hatch event triggered");
    this.emitter.send(createdEvent);
    log.info("egg time checked");
  }
}
