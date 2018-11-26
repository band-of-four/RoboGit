package org.robogit.repository

import org.robogit.domain.Interface
import org.robogit.domain.Sensor
import org.springframework.data.repository.CrudRepository

interface SensorRepository: CrudRepository<Sensor, Int> {
  /**
   * Finds all [Sensor]s pertain to the [sensors].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with [Sensor]s.
   */
  fun findByMinVoltageBetween(from: Float, to: Float): List<Sensor>

  /**
   * Finds all [Sensor]s pertain to the [sensors].
   * @param minVoltage is the left search boundary.
   * @return List with [Sensor]s.
   */
  fun findByMinVoltageGreaterThan(minVoltage: Float): List<Sensor>

  /**
   * Finds all [Sensor]s pertain to the [sensors].
   * @param minVoltage is the right search boundary.
   * @return List with [Sensor]s.
   */
  fun findByMinVoltageLessThan(minVoltage: Float): List<Sensor>

  /**
   * Finds all [Sensor]s pertain to the [sensors].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with [Sensor]s.
   */
  fun findByMaxVoltageBetween(from: Float, to: Float): List<Sensor>

  /**
   * Finds all [Sensor]s pertain to the [sensors].
   * @param minVoltage is the left search boundary.
   * @return List with [Sensor]s.
   */
  fun findByMaxVoltageGreaterThan(maxVoltage: Float): List<Sensor>

  /**
   * Finds all [Sensor]s pertain to the [sensors].
   * @param minVoltage is the right search boundary.
   * @return List with [Sensor]s.
   */
  fun findByMaxVoltageLessThan(maxVoltage: Float): List<Sensor>

  /**
   * Finds all [Sensor]s pertain to the [sensors].
   * @param sensorInterface is an [Interface] with which [Sensor]s will be selected..
   * @return List with [Sensor]s.
   */
  fun findBySensorInterface(sensorInterface: Interface): List<Sensor>
}
