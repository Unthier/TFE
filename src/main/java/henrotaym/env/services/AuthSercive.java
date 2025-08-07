package henrotaym.env.services;

import henrotaym.env.Factories.UserFactory;
import henrotaym.env.entities.User;
import henrotaym.env.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthSercive {
  private static final String SECRET =
      "uneSuperCleUltraSecrete_qui_doit_faire_au_moins_32_caracteres!";
  private static final long EXPIRATION_TIME = 86400000; // 1 jour en ms

  private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
  private final UserRepository userRepository;
  private final UserFactory userFactory;

  public String generateToken(String username) {
    User user = this.userRepository.findByName(username).orElse(null);
    if (user == null) {
      user = this.userFactory.create(username);
      this.userRepository.save(user);
    }
    // return null;
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public User getUserFromToken(String token) {
    return null;
    // String username =
    //     Jwts.parserBuilder()
    //         .setSigningKey(key)
    //         .build()
    //         .parseClaimsJws(token)
    //         .getBody()
    //         .getSubject();

    // return this.userRepository
    //     .findByName(username)
    //     .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }
}
