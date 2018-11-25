package org.robogit.repository

import org.robogit.domain.Interface
import org.robogit.domain.Sensor
import org.springframework.data.repository.CrudRepository

interface SensorRepository: CrudRepository<Sensor, Int> {
  fun findByMinVoltageBetween(from: Float, to: Float): List<Sensor>

  fun findByMinVoltageGreaterThan(minVoltage: Float): List<Sensor>

  fun findByMinVoltageLessThan(minVoltage: Float): List<Sensor>

  fun findByMaxVoltageBetween(from: Float, to: Float): List<Sensor>

  fun findByMaxVoltageGreaterThan(maxVoltage: Float): List<Sensor>

  fun findByMaxVoltageLessThan(maxVoltage: Float): List<Sensor>

  fun findBySensorInterface(sensorInterface: Interface): List<Sensor>
}
