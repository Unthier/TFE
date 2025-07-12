package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import henrotaym.env.enums.PokemonCatchingStatusName;
import henrotaym.env.enums.PokemonTypeName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pokemons_catching")
public class PokemonCatching {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private BigInteger pokemonId;

  private String name;

  private String nickname;

  private Integer attack;

  private Integer speed;

  private Integer pv;

  private Integer level;

  private Integer defense;

  private String nameOfNextEvolution;

  private Integer levelOfNextEvolution;

  private LocalDateTime catchingOn;

  private Integer damage;

  @OneToMany(mappedBy = "pokemonCatching", cascade = CascadeType.ALL)
  private List<PokemonTraining> trainings;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PokemonCatchingStatusName status;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PokemonTypeName type;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference() // Prevents infinite recursion during serialization
  private User user;
}
