package org.robogit.domain

import lombok.EqualsAndHashCode
import org.hibernate.annotations.Check
import org.robogit.config.DatabaseConfig
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Класс, описывающий сущность Мотор, хнанит ифнормацию о моторах
 * model - модель сенсора
 * min_voltage - минимальное напряжение
 * max_voltage - максимальное напряжение
 * power - номинальная мощность
 */
@Entity
@Table(name = "motors", schema = DatabaseConfig.SCHEMA_NAME)
@Check(constraints = "max_voltage > min_voltage")
@EqualsAndHashCode
class Motor {
  @Id
  @Column
  var id: Int? = null

  @OneToOne
  @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
  var information: Information? = null

  @Column
  @Min(0)
  var power: Float? = null

  @Column
  var minVoltage: Float? = null

  @Column
  var maxVoltage: Float? = null

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "interface_id")
  @NotNull
  var motorInterface: Interface? = null
}
