package org.robogit.repository

import org.robogit.domain.Interface
import org.robogit.domain.Motor
import org.springframework.data.repository.CrudRepository

interface MotorRepository: CrudRepository<Motor, Int> {
  fun findByMinVoltageBetween(from: Float, to: Float): List<Motor>

  fun findByMinVoltageGreaterThan(minVoltage: Float): List<Motor>

  fun findByMinVoltageLessThan(minVoltage: Float): List<Motor>

  fun findByMaxVoltageBetween(from: Float, to: Float): List<Motor>

  fun findByMaxVoltageGreaterThan(maxVoltage: Float): List<Motor>

  fun findByMaxVoltageLessThan(maxVoltage: Float): List<Motor>

  fun findByPowerBetween(from: Float, to: Float): List<Motor>

  fun findByPowerGreaterThan(power: Float): List<Motor>

  fun findByPowerLessThan(power: Float): List<Motor>

  fun findByMotorInterface(motorInterface: Interface): List<Motor>
}
