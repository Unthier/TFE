package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import henrotaym.env.enums.FightStateName;
import jakarta.persistence.Column;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "fights")
public class Fight {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private BigInteger pokemonCatchingId;

  private BigInteger pokemonTrainerId;

  private Integer pokemonCatchingDamage;

  private Integer pokemonTrainerDamage;

  private Integer tours;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FightStateName state;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false) // colonne dans la table `fights`
  @JsonBackReference
  private User user;
}
