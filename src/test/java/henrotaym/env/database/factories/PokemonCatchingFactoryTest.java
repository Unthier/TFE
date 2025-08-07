package henrotaym.env.database.factories;

import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.enums.PokemonCatchingStatusName;
import henrotaym.env.enums.PokemonTypeName;
import java.math.BigInteger;
import java.time.LocalDateTime;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class PokemonCatchingFactoryTest extends EntityFactoryTest<PokemonCatching> {

  private UserFactoryTest userFactoryTest;

  public PokemonCatchingFactoryTest(
      Faker faker,
      JpaRepository<PokemonCatching, BigInteger> repository,
      UserFactoryTest userFactoryTest) {
    super(faker, repository);
    this.userFactoryTest = userFactoryTest;
  }

  @Override
  protected PokemonCatching entity() {
    return new PokemonCatching();
  }

  @Override
  protected void attributes(PokemonCatching entity) {
    entity.setAttack(this.faker.number().positive());
    entity.setDefense(this.faker.number().positive());
    entity.setLevel(this.faker.number().positive());
    entity.setSpeed(this.faker.number().positive());
    entity.setPv(this.faker.number().positive());
    entity.setCatchingOn(LocalDateTime.now());
    entity.setLevelOfNextEvolution(this.faker.number().positive());
    entity.setNameOfNextEvolution(this.faker.pokemon().name());
    entity.setName(this.faker.pokemon().name());
    entity.setDamage(0);
    entity.setStatus(PokemonCatchingStatusName.NEUTRE);
    entity.setType(PokemonTypeName.BUG);
  }

  @Override
  protected void relationships(PokemonCatching entity) {
    entity.setUser(this.userFactoryTest.create());
  }
}
