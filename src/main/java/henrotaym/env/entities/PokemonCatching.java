package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import henrotaym.env.enums.PokemonCatchingStatusName;
import henrotaym.env.enums.PokemonTypeName;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

  private BigInteger pokemon_id;

  private String name;

  private String nickname;

  private Integer attack;

  private Integer speed;

  private Integer pv;

  private Integer level;

  private Integer defense;

  @CreationTimestamp private Instant catchingOn;

  @ElementCollection
  @CollectionTable(
      name = "pokemon_training_ons",
      joinColumns = @JoinColumn(name = "pokemon_catching_id"))
  private List<Instant> trainingOns;

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
