package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import henrotaym.env.enums.UserRoleName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRoleName role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<PokemonCatching> pokemonsCatching = new ArrayList<PokemonCatching>();

  //   public void setPokemonsCatching(List<PokemonCatching> pokemonCatchings) {
  //     this.pokemonsCatching.clear();
  //     this.pokemonsCatching.addAll(pokemonCatchings);
  //   }

  public Set<String> getIncludables() {
    return Set.of("pokemonsCatching");
  }
}
