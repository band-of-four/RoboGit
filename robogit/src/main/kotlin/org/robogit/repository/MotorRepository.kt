package org.robogit.repository

import org.robogit.domain.Information
import org.robogit.domain.Interface
import org.robogit.domain.Motor
import org.robogit.dto.MegaInformationDto
import org.robogit.dto.MotorSumDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface MotorRepository: CrudRepository<Motor, Int> {

  /**
   * Finds all [Motor]s which [Motor.minVoltage] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByMinVoltageBetween(from: Float, to: Float): List<Motor>

  /**
   * Finds all [Motor]s which [Motor.minVoltage] is greater than [minVoltage].
   * @param minVoltage is the left search boundary.
   * @return List with results.
   */
  fun findByMinVoltageGreaterThan(minVoltage: Float): List<Motor>

  /**
   * Finds all [Motor]s which [Motor.minVoltage] is less than [minVoltage].
   * @param minVoltage is the right search boundary.
   * @return List with results.
   */
  fun findByMinVoltageLessThan(minVoltage: Float): List<Motor>

  /**
   * Finds all [Motor]s which [Motor.maxVoltage] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageBetween(from: Float, to: Float): List<Motor>

  /**
   * Finds all [Motor]s which [Motor.maxVoltage] is greater than [maxVoltage].
   * @param maxVoltage is the left search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageGreaterThan(maxVoltage: Float): List<Motor>

  /**
   * Finds all [Motor]s which [Motor.maxVoltage] is less than [maxVoltage].
   * @param maxVoltage is the right search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageLessThan(maxVoltage: Float): List<Motor>

  /**
   * Finds all [Motor]s which [Motor.power] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByPowerBetween(from: Float, to: Float): List<Motor>

  /**
   * Finds all [Motor]s which [Motor.power] is greater than [power].
   * @param power is the left search boundary.
   * @return List with results.
   */
  fun findByPowerGreaterThan(power: Float): List<Motor>

  /**
   * Finds all [Motor]s which [Motor.power] is less than [power].
   * @param power is the right search boundary.
   * @return List with results.
   */
  fun findByPowerLessThan(power: Float): List<Motor>

  /**
   * Finds all [Motor]s with specified [Motor.motorInterface].
   * @param motorInterface is an [Interface] with which [Motor]s will be selected.
   * @return List with results.
   */
  fun findByMotorInterface(motorInterface: Interface): List<Motor>

  /**
   * Возвращает все моторы, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @return лист результатов
   */
  @Query("SELECT new org.robogit.dto.MotorSumDto(m, sum(p.amount)as s)  FROM Motor m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc nulls last")
  fun findPopular(): List<MotorSumDto?>?

  /**
   * Возвращает страницу моторов, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   * @return дист результатов
   */
  @Query("SELECT new org.robogit.dto.MotorSumDto(m, sum(p.amount)as s)  FROM Motor m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc nulls last")
  fun findPagePopular(pageable: Pageable): Page<MotorSumDto?>?

  /**
   * Возвращает мотор по id
   * @param id - ид мотора
   * @return мотор
   */
  @Query("SELECT m FROM Motor m WHERE m.id = :id")
  fun findMotorById(@Param("id") id: Int) : Motor?

  /**
   * Применяет фильтры к маторам
   * @param min_price - минимальная цена
   * @param max_price - максимальная цена
   * @param min_min_voltage - минимальное мин.значение voltage
   * @param max_min_voltage - максимальное мин.значение voltage
   * @param min_max_voltage - минимальное макс.значение voltage
   * @param max_max_voltage - максимальное макс.значение voltage
   * @param min_power - минимальная мощности
   * @param max_power - максимальная мощности
   * @return страницу результата
   */
  @Query("SELECT mi FROM Motor m JOIN m.information mi WHERE" +
          "(:min_price IS NULL OR :min_price < mi.price) AND" +
          "(:max_price IS NULL OR :max_price > mi.price) AND" +
          "(:min_min_voltage IS NULL OR :min_min_voltage < m.minVoltage) AND" +
          "(:max_min_voltage IS NULL OR :max_min_voltage > m.minVoltage) AND" +
          "(:min_max_voltage IS NULL OR :min_max_voltage < m.maxVoltage) AND" +
          "(:max_max_voltage IS NULL OR :max_max_voltage > m.maxVoltage) AND" +
          "(:min_power IS NULL OR :min_power < m.power) AND" +
          "(:max_power IS NULL OR :max_power > m.power)")
  fun filter( pagable: Pageable,
              @Param("min_price") min_price: Float?,
              @Param("max_price") max_price: Float?,
              @Param("min_min_voltage") min_min_voltage: Float?,
              @Param("max_min_voltage") max_min_voltage: Float?,
              @Param("min_max_voltage") min_max_voltage: Float?,
              @Param("max_max_voltage") max_max_voltage: Float?,
              @Param("min_power") min_power: Float?,
              @Param("max_power") max_power: Float?) : Page<Information>

  /**
   * Возвращает полную информацию о моторе
   * @param id - ид мотора
   * @return результат
   */
  @Query("SELECT new org.robogit.dto.MegaInformationDto(i, m) FROM Motor m JOIN Information i ON i.id = m.id WHERE i.id = :id")
  fun selectMegaInformationMotorById(@Param("id") id: Int): MegaInformationDto?
}
