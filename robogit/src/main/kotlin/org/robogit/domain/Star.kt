package org.robogit.domain

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "stars", schema = "s244707")
class Star{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stars_gen")
    @SequenceGenerator(name = "stars_gen", sequenceName = "stars_id_seq")
    @Column
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NotNull
    var user: User? = null

    @ManyToOne
    @JoinColumn(name = "id_repository")
    @NotNull
    var repository: Repository? = null
}