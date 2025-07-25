package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import henrotaym.env.enums.TrainerStatusName;
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
import java.math.BigInteger;
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
@Table(name = "trainers")
public class Trainer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private String name;

  @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<PokemonTrainer> pokemons;

  public void setPokemonsCatching(List<PokemonTrainer> pokemonTrainers) {
    this.pokemons.clear();
    this.pokemons.addAll(pokemonTrainers);
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TrainerStatusName status;

  public Set<String> getIncludables() {
    return Set.of("pokemons");
  }
}
