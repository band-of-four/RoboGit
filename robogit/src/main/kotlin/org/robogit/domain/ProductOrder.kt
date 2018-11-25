package org.robogit.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.EqualsAndHashCode
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Класс, описывающий сущность, определяющую какие товары находятся в определенном заказе
 * part_number - номер товара
 * id_order - номер заказа
 * amount - количество данного товара в заказе
 * unit_price - цена заказа на момент совершения покупки
 */
@Entity
@Table(name = "products_orders", schema = "s244707")
@EqualsAndHashCode(of = ["id"])
class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_orders_gen")
    @SequenceGenerator(name = "products_orders_gen", sequenceName = "products_orders_id_seq")
    @Column
    var id: Int? = null

    @Column
    @Min(0)
    @NotNull
    var amount: Int? = null

    @Column
    @Min(0)
    @NotNull
    var unit_price: Float? = null

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_order")
    var order: Order? = null

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_product")
    var information: Information? = null

}