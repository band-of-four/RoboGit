package org.robogit.domain

import lombok.EqualsAndHashCode
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "products_orders", schema = "s244707")
@EqualsAndHashCode(of = ["id"])
class ProductOrder{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_orders_gen")
    @SequenceGenerator(name = "products_orders_gen", sequenceName = "products_orders_id_seq")
    @Column
    var id: Int? = null

    @Column
    @Min(0)
    var amount: Int? = null

    @Column
    @Min(0)
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