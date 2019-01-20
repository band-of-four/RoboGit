package org.robogit.repository

import org.robogit.domain.Interface
import org.robogit.domain.Sensor
import org.robogit.dto.SensorSumDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

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

  /**
   * Возвращает все сенсоры, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   */
  @Query("SELECT new org.robogit.dto.SensorSumDto(s, sum(p.amount)as sm)  FROM Sensor s, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN s.information i2 WHERE i.id=i2.id GROUP BY s.id ORDER BY sm desc")
  fun findPopular(): List<SensorSumDto?>?

  /**
   * Возвращает страницу сенсоров, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   */
  @Query("SELECT new org.robogit.dto.SensorSumDto(m, sum(p.amount)as s)  FROM Sensor m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc")
  fun findPagePopular(pageable: Pageable): Page<SensorSumDto?>?

  @Query("SELECT s FROM Sensor s WHERE s.id = :id")
  fun findSensorById(@Param("id") id: Int) : Sensor
}
