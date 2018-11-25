package org.robogit.domain

import lombok.EqualsAndHashCode
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "orders", schema = "s244707")
@EqualsAndHashCode(of = ["id"])
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_gen")
    @SequenceGenerator(name = "order_gen", sequenceName = "order_id_seq")
    @Column
    var id: Int? = null

    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    var date: Date? = null

    @Column
    var address: String? = null

    @Column
    var paymentType: String? = null

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NotNull
    var user: User? = null;
}