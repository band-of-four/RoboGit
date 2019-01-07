package org.robogit.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.EqualsAndHashCode
import org.robogit.config.DatabaseConfig
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Класс, описывающий сущность, определяющую какие товары находятся в определенной корзине
 * part_number - номер товара
 * id_user - номер пользователя
 * amount - количество данного товара в корзине
 */
@Entity
@Table(name = "products_users", schema = DatabaseConfig.SCHEMA_NAME)
@EqualsAndHashCode(of = ["id"])
class ProductUser {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_users_gen")
  @SequenceGenerator(name = "products_users_gen", sequenceName = "products_users_id_seq")
  @Column
  var id: Int? = null

  @Column
  @Min(0)
  @NotNull
  var amount: Int? = null

  @JsonIgnore
  @ManyToOne
  @NotNull
  @JoinColumn(name = "id_user")
  var user: User? = null

  @ManyToOne
  @NotNull
  @JoinColumn(name = "id_product")
  var information: Information? = null
}
