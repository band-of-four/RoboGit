package org.robogit.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users", schema = "s244707")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_id_seq")
    @Column
    var id: Int? = null

    @NotBlank
    @Column
    var login: String? = null

    @NotBlank
    @Column
    var password: String? = null

    @Column
    var telegramId: String? = null

    @OneToMany(mappedBy = "user")
    var orders: Set<Order> = HashSet();
}