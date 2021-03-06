package org.robogit.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import org.robogit.config.DatabaseConfig
import javax.persistence.*
import javax.validation.constraints.NotBlank


/**
 * Класс, описывающий сущность Интерфейс
 * name - название интерфейса
 */
@Entity
@Table(name = "interfaces", schema = DatabaseConfig.SCHEMA_NAME)
class Interface {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interface_gen")
  @SequenceGenerator(name = "interface_gen", sequenceName = "interface_id_seq")
  @Column
  var id: Int? = null

  @Column(unique = true)
  @NotBlank
  var name: String? = null

  @JsonIgnore
  @OneToMany(mappedBy = "controllerInterface")
  var controllers: Set<Controller> = HashSet()

  @JsonIgnore
  @OneToMany(mappedBy = "motorInterface")
  var motors: Set<Motor> = HashSet()

/*
    @ManyToMany
    @JoinTable(name = "details_interfaces",
            joinColumns = arrayOf(JoinColumn(name="interface_id", referencedColumnName = "id")),
            inverseJoinColumns = arrayOf(JoinColumn(name="detail_id", referencedColumnName = "id"))
    )
    var platforms: Set<Platform> = HashSet()*/
}
