package org.robogit.repository

import org.robogit.domain.Information
import org.robogit.domain.Interface
import org.robogit.domain.Sensor
import org.robogit.dto.MegaInformationDto
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
   * @return лист результатов
   */
  @Query("SELECT new org.robogit.dto.SensorSumDto(s, sum(p.amount)as sm)  FROM Sensor s, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN s.information i2 WHERE i.id=i2.id GROUP BY s.id ORDER BY sm desc")
  fun findPopular(): List<SensorSumDto?>?

  /**
   * Возвращает страницу сенсоров, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   * @return лист результатов
   */
  @Query("SELECT new org.robogit.dto.SensorSumDto(m, sum(p.amount)as s)  FROM Sensor m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc")
  fun findPagePopular(pageable: Pageable): Page<SensorSumDto?>?

  /**
   * Возвращает сенсоры по ид
   * @param id - ид сенсора
   * @return лист результатов
   */
  @Query("SELECT s FROM Sensor s WHERE s.id = :id")
  fun findSensorById(@Param("id") id: Int) : Sensor?

  /**
   * Применяет фильтры к сенсорам
   * @param min_price - минимальная цена
   * @param max_price - максимальная цена
   * @param min_min_voltage - минимальное мин.значение voltage
   * @param max_min_voltage - максимальное мин.значение voltage
   * @param min_max_voltage - минимальное макс.значение voltage
   * @param max_max_voltage - максимальное макс.значение voltage
   * @return страницу результата
   */
  @Query("SELECT si FROM Sensor s JOIN s.information si WHERE " +
          "si.type = org.robogit.domain.Type.SENSOR AND" +
          "(:min_price IS NULL OR :min_price < si.price) AND" +
          "(:max_price IS NULL OR :max_price > si.price) AND" +
          "(:min_min_voltage IS NULL OR :min_min_voltage < s.minVoltage) AND" +
          "(:max_min_voltage IS NULL OR :max_min_voltage > s.minVoltage) AND" +
          "(:min_max_voltage IS NULL OR :min_max_voltage < s.maxVoltage) AND" +
          "(:max_max_voltage IS NULL OR :max_max_voltage > s.maxVoltage)")
  fun filter( pagable: Pageable,
              @Param("min_price") min_price: Float?,
              @Param("max_price") max_price: Float?,
              @Param("min_min_voltage") min_min_voltage: Float?,
              @Param("max_min_voltage") max_min_voltage: Float?,
              @Param("min_max_voltage") min_max_voltage: Float?,
              @Param("max_max_voltage") max_max_voltage: Float?) : Page<Information>

  /**
   * Возвращает полную информацию о сенсоре
   * @param id - ид мотора
   * @return результат
   */
  @Query("SELECT new org.robogit.dto.MegaInformationDto(i, m) FROM Sensor m JOIN Information i ON i.id = m.id WHERE i.id = :id")
  fun selectMegaInformationSensorById(@Param("id") id: Int): MegaInformationDto?
}
