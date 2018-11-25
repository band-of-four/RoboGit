package org.robogit.domain

import lombok.EqualsAndHashCode
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "products_users", schema = "s244707")
@EqualsAndHashCode(of = ["id"])
class ProductUser{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_users_gen")
    @SequenceGenerator(name = "products_users_gen", sequenceName = "products_users_id_seq")
    @Column
    var id: Int? = null

    @Column
    @Min(0)
    var amount: Int? = null

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_user")
    var user: User? = null

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_product")
    var information: Information? = null
}