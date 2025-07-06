package henrotaym.env.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private String name;

  @Pattern(
      regexp =
          "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*])[A-Za-z\\d!@#\\$%\\^&\\*]{8,}$",
      message =
          "Le mot de passe doit contenir au moins 8 caractères, dont une majuscule, une minuscule,"
              + " un chiffre et un caractère spécial")
  private String password;

  private String mail;

  //   @CreationTimestamp
  //     private Instant captchingNumber;
  //   private Integer captchingNumber;
}
