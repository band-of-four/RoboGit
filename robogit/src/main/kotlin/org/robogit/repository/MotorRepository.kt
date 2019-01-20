package org.robogit.repository

import org.robogit.domain.Interface
import org.robogit.domain.Motor
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
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc")
  fun findPopular(): List<MotorSumDto?>?

  /**
   * Возвращает страницу моторов, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   * @return дист результатов
   */
  @Query("SELECT new org.robogit.dto.MotorSumDto(m, sum(p.amount)as s)  FROM Motor m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc")
  fun findPagePopular(pageable: Pageable): Page<MotorSumDto?>?

  /**
   * Возвращает мотор по id
   * @param id - ид мотора
   * @return мотор
   */
  @Query("SELECT m FROM Motor m WHERE m.id = :id")
  fun findMotorById(@Param("id") id: Int) : Motor
}
