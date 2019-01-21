package org.robogit.domain

import lombok.EqualsAndHashCode
import org.robogit.config.DatabaseConfig
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Class contains information about a product in the related [card]
 */
@Entity
@Table(name = "products_cards", schema = DatabaseConfig.SCHEMA_NAME)
@EqualsAndHashCode(of = ["id"])
class ProductCard {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_cards_gen")
  @SequenceGenerator(name = "products_cards_gen", sequenceName = "products_cards_id_seq")
  @Column
  var id: Int? = null

  @Column
  @Min(0)
  @NotNull
  var amount: Int? = null

//  @Column
//  @Min(0)
//  @NotNull
//  var unit_price: Float? = null

  @ManyToOne
  @NotNull
  @JoinColumn(name = "id_card")
  var card: Card? = null

  @ManyToOne
  @JoinColumn(name = "id_product")
  var information: Information? = null
}
