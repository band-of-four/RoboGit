package org.robogit.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "interfaces", schema = "s244707")
class Interface{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interface_gen")
    @SequenceGenerator(name = "interface_gen", sequenceName = "interface_id_seq")
    @Column
    var id: Int? = null

    @Column(unique = true)
    @NotBlank
    var name : String? = null

    @JsonIgnore
    @OneToMany(mappedBy = "controllerInterface")
    var controllers: Set<Controller> = HashSet();
}