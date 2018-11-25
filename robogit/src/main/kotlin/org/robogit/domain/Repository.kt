package org.robogit.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "repositories", schema = "s244707")
class Repository{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repository_gen")
    @SequenceGenerator(name = "repository_gen", sequenceName = "repositories_id_seq")
    @Column
    var id: Int? = null

    @Column
    @NotBlank
    var path: String? = null

    @Column
    var description: String? = null

    @Column
    @NotBlank
    var name: String? = null

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_user")
    var user: User? = null

    @OneToMany(mappedBy = "repository")
    var stars: Set<Star> = HashSet()
}