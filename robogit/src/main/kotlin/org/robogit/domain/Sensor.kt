package org.robogit.domain

import lombok.EqualsAndHashCode
import org.hibernate.annotations.Check
import org.robogit.config.DatabaseConfig
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Класс, описывающий сущность Сенсора
 *  min_voltage - минимальное значнеие
 *  max_voltage - максимальное напряжение
 */
@Entity
@Table(name = "controllers", schema = DatabaseConfig.SCHEMA_NAME)
@Check(constraints = "max_voltage > min_voltage")
@EqualsAndHashCode
class Sensor {
  @Id
  @Column
  var id: Int? = null

  @OneToOne
  @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
  var information: Information? = null

  @Column
  var minVoltage: Float? = null

  @Column
  @Min(0)
  var maxVoltage: Float? = null

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "interface_id")
  @NotNull
  var sensorInterface: Interface? = null
}
