package org.robogit.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.EqualsAndHashCode
import org.robogit.config.DatabaseConfig
import java.util.HashSet
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * Class contains information about filled card.
 * user - card owner
 * productCards - set of products which located in the card
 */
@Entity
@Table(name = "cards", schema = DatabaseConfig.SCHEMA_NAME)
@EqualsAndHashCode(of = ["id"])
class Card {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_gen")
  @SequenceGenerator(name = "card_gen", sequenceName = "cards_id_seq")
  @Column
  var id: Int? = null

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "id_user")
  @NotNull
  var user: User? = null

  @JsonIgnore
  @OneToMany(mappedBy = "card")
  var productCards: Set<ProductCard> = HashSet()
}
