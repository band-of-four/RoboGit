package org.robogit.repository

import org.robogit.domain.Controller
import org.robogit.domain.Platform
import org.robogit.dto.PlatformSumDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface PlatformRepository: CrudRepository<Platform, Int> {

  /**
   * Finds all [Platform]s which [Platform.ram] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByRamBetween(from: Int, to: Int): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.ram] is greater than [ram].
   * @param ram is the left search boundary.
   * @return List with results.
   */
  fun findByRamGreaterThan(ram: Int): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.ram] is less than [ram].
   * @param ram is the right search boundary.
   * @return List with results.
   */
  fun findByRamLessThan(ram: Int): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.minVoltage] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByMinVoltageBetween(from: Float, to: Float): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.minVoltage] is greater than [minVoltage].
   * @param minVoltage is the left search boundary.
   * @return List with results.
   */
  fun findByMinVoltageGreaterThan(minVoltage: Float): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.minVoltage] is less than [minVoltage].
   * @param minVoltage is the right search boundary.
   * @return List with results.
   */
  fun findByMinVoltageLessThan(minVoltage: Float): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.maxVoltage] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageBetween(from: Float, to: Float): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.maxVoltage] is greater than [maxVoltage].
   * @param maxVoltage is the left search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageGreaterThan(maxVoltage: Float): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.maxVoltage] is less than [maxVoltage].
   * @param maxVoltage is the right search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageLessThan(maxVoltage: Float): List<Platform>

  /**
   * Finds all [Platform]s which [Platform.maxVoltage] is less than [maxVoltage].
   * @param controller is a controller of product which will be selected.
   * @return List with results.
   */
  fun findByController(controller: Controller): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.analogInputs] (i.e. count of analog inputs) between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByAnalogInputsBetween(from: Int, to: Int): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.analogInputs] (i.e. count of analog inputs) greater than [analogInputs].
   * @param analogInputs is the left search boundary.
   * @return List with results.
   */
  fun findByAnalogInputsGreaterThan(analogInputs: Int): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.analogInputs] (i.e. count of analog inputs) less than [analogInputs].
   * @param analogInputs is the right search boundary.
   * @return List with results.
   */
  fun findByAnalogInputsLessThan(analogInputs: Int): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.flashmemory] (i.e. count of analog inputs) between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByFlashmemoryBetween(from: Int, to: Int): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.flashmemory] (i.e. count of analog inputs) greater than [flashmemory].
   * @param analogInputs is the left search boundary.
   * @return List with results.
   */
  fun findByFlashmemoryGreaterThan(flashmemory: Int): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.flashmemory] (i.e. count of analog inputs) less than [flashmemory].
   * @param analogInputs is the right search boundary.
   * @return List with results.
   */
  fun findByFlashmemoryLessThan(flashmemory: Int): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.freg] (i.e. count of analog inputs) between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByFreqBetween(from: Float, to: Float): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.freg] (i.e. count of analog inputs) greater than [freg].
   * @param analogInputs is the left search boundary.
   * @return List with results.
   */
  fun findByFreqGreaterThan(freq: Float): List<Platform>

  /**
   * Finds all [Platform]s with [Platform.freg] (i.e. count of analog inputs) less than [freg].
   * @param analogInputs is the right search boundary.
   * @return List with results.
   */
  fun findByFreqLessThan(freq: Float): List<Platform>

  /**
   * Возвращает все платформы, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   */
  @Query("SELECT new org.robogit.dto.PlatformSumDto(pl, sum(p.amount)as s)  FROM Platform pl, ProductUser p " +
          "JOIN p.information i " +
          "JOIN pl.information i2 WHERE i.id=i2.id GROUP BY pl.id ORDER BY s desc")
  fun findPopular(): List<PlatformSumDto?>?

  /**
   * Возвращает страницу платформ, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   */
  @Query("SELECT new org.robogit.dto.PlatformSumDto(m, sum(p.amount)as s)  FROM Platform m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc")
  fun findPagePopular(pageable: Pageable): Page<PlatformSumDto?>?

  @Query("SELECT p FROM Platform p WHERE p.id = :id")
  fun findPlatformById(@Param("id") id: Int) : Platform
}
