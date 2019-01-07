package org.robogit.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.EqualsAndHashCode
import org.robogit.config.DatabaseConfig
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Класс, описывающий сущность Заказа, хнанит ифнормацию о заказах
 * price - сумма всех покупок в заказе
 * date - дата создания заказа
 * address - адрес получателя
 */
@Entity
@Table(name = "orders", schema = DatabaseConfig.SCHEMA_NAME)
@EqualsAndHashCode(of = ["id"])
class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_gen")
  @SequenceGenerator(name = "order_gen", sequenceName = "orders_id_seq")
  @Column
  var id: Int? = null

  @Column
  @Temporal(javax.persistence.TemporalType.TIMESTAMP)
  var date: Date? = null

  @Column
  @NotBlank
  var address: String? = null

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "id_user")
  @NotNull
  var user: User? = null

  @JsonIgnore
  @OneToMany(mappedBy = "order")
  var productOrders: Set<ProductOrder> = HashSet()
}
