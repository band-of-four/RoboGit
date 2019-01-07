package org.robogit.domain

import lombok.EqualsAndHashCode
import org.hibernate.annotations.Check
import org.robogit.config.DatabaseConfig
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 *  Класс, описывающий сущность Контроллер
 *  ram - размер оперативной памяти
 *  min_voltage - минимальное значнеие
 *  max_voltage - максимальное напряжение
 *  analog_inputs - количество аналоговых входов
 */
@Entity
@Table(name = "controllers", schema = DatabaseConfig.SCHEMA_NAME)
@Check(constraints = "max_voltage > min_voltage")
@EqualsAndHashCode
class Controller : Serializable {
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
  @Min(0)
  var minVoltage: Float? = null

  @Column
  var maxVoltage: Float? = null

  @Column
  var analogInputs: Int? = null

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "interface_id")
  @NotNull
  var controllerInterface: Interface? = null
//
//    @OneToMany(mappedBy = "controller")
//    var platforms: Set<Platform> = HashSet();
}
