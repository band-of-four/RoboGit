package org.robogit.domain

import lombok.EqualsAndHashCode
import org.hibernate.annotations.Check
import org.robogit.config.DatabaseConfig
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Класс, описывающий сущность Платформы, хранит информаю о платформах для сборки робота
 * min_voltage - минимальное напряжение
 * max_voltage - максимальное напряжение
 * freq - номинальная частота процессора
 * analog_inputs - число аналоговых входов
 * flash_memory - размер флеш-памяти
 * ram - размер оперативной памяти
 */
@Entity
@Table(name = "platforms", schema = DatabaseConfig.SCHEMA_NAME)
@Check(constraints = "max_voltage > min_voltage")
@EqualsAndHashCode
class Platform {

  @Id
  @Column
  var id: Int? = null

  @OneToOne
  @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
  var information: Information? = null

  @Column
  @Min(0)
  var ram: Int? = null

  @Column
  var minVoltage: Float? = null

  @Column
  @Min(0)
  var maxVoltage: Float? = null

  @Column
  var analogInputs: Int? = null

  @Column
  @Min(0)
  var freq: Float? = null

  @Column
  var flashmemory: Int? = null

  @ManyToOne
  @JoinColumn(name = "controller")
  @NotNull
  var controller: Controller? = null

  @ManyToMany
  @JoinTable(name = "details_interfaces",
      inverseJoinColumns = arrayOf(JoinColumn(name = "interface_id", referencedColumnName = "id")),
      joinColumns = arrayOf(JoinColumn(name = "detail_id", referencedColumnName = "id"))
  )
  var interfaces: Set<Interface> = HashSet()
}
