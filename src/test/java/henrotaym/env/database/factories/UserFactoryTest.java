package henrotaym.env.database.factories;

import henrotaym.env.entities.User;
import henrotaym.env.enums.UserRoleName;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserFactoryTest extends EntityFactoryTest<User> {

  public UserFactoryTest(Faker faker, JpaRepository<User, BigInteger> repository) {
    super(faker, repository);
  }

  @Override
  protected User entity() {
    return new User();
  }

  @Override
  protected void attributes(User entity) {
    entity.setMail(this.faker.internet().emailAddress());
    entity.setName(this.faker.name().name());
    entity.setPassword("Abcde123!");
    entity.setRole(UserRoleName.USER);
  }
}
