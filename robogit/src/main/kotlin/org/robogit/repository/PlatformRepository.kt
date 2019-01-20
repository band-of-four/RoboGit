package org.robogit.repository

import org.robogit.domain.Controller
import org.robogit.domain.Platform
import org.robogit.dto.PlatformSumDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.RequestParam
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
   * @return лист результатов
   */
  @Query("SELECT new org.robogit.dto.PlatformSumDto(pl, sum(p.amount)as s)  FROM Platform pl, ProductUser p " +
          "JOIN p.information i " +
          "JOIN pl.information i2 WHERE i.id=i2.id GROUP BY pl.id ORDER BY s desc")
  fun findPopular(): List<PlatformSumDto?>?

  /**
   * Возвращает страницу платформ, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   * @return лист результатов
   */
  @Query("SELECT new org.robogit.dto.PlatformSumDto(m, sum(p.amount)as s)  FROM Platform m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc")
  fun findPagePopular(pageable: Pageable): Page<PlatformSumDto?>?

  /**
   * Возвращает платформу по ид
   * @param id - ид детали
   *
   */
  @Query("SELECT p FROM Platform p WHERE p.id = :id")
  fun findPlatformById(@Param("id") id: Int) : Platform?

  /**
   * Применяет фильтры к платформе
   * @param min_price - минимальная цена
   * @param max_price - максимальная цена
   * @param min_min_voltage - минимальное мин.значение voltage
   * @param max_min_voltage - максимальное мин.значение voltage
   * @param min_max_voltage - минимальное макс.значение voltage
   * @param max_max_voltage - максимальное макс.значение voltage
   * @param min_freq - минимальная частота
   * @param max_freq - максимальная частота
   * @param min_analog_inputs - минимальное кол-во аналоговых входов
   * @param max_analog_inputs - максимальное кол-во аналоговых входов
   * @param min_flashmemory - минимальное значение оперативной памяти
   * @param max_flashmemory - максимальное значение оперативной памяти
   * @param min_ram - минимальное значение ram
   * @param max_ram - максимальное значение ram
   * @return страницу результата
   */
  @Query("SELECT p FROM Platform p JOIN p.information pi WHERE" +
          "(:min_price IS NULL OR :min_price < pi.price) AND" +
          "(:max_price IS NULL OR :max_price > pi.price) AND" +
          "(:min_min_voltage IS NULL OR :min_min_voltage < p.minVoltage) AND" +
          "(:max_min_voltage IS NULL OR :max_min_voltage > p.minVoltage) AND" +
          "(:min_max_voltage IS NULL OR :min_max_voltage < p.maxVoltage) AND" +
          "(:max_max_voltage IS NULL OR :max_max_voltage > p.maxVoltage) AND" +
          "(:min_freq IS NULL OR :min_freq < p.freq) AND" +
          "(:max_freq IS NULL OR :max_freq > p.freq) AND" +
          "(:min_analog_inputs IS NULL OR :min_analog_inputs < p.analogInputs) AND" +
          "(:max_analog_inputs IS NULL OR :max_analog_inputs > p.analogInputs) AND" +
          "(:min_flashmemory IS NULL OR :min_flashmemory < p.flashmemory) AND" +
          "(:max_flashmemory IS NULL OR :max_flashmemory > p.flashmemory) AND" +
          "(:min_ram IS NULL OR :min_ram < p.ram) AND" +
          "(:max_ram IS NULL OR :max_ram > p.ram)")
  fun filter( pagable: Pageable,
              @Param("min_price") min_price: Float?,
              @Param("max_price") max_price: Float?,
              @Param("min_min_voltage") min_min_voltage: Float?,
              @Param("max_min_voltage") max_min_voltage: Float?,
              @Param("min_max_voltage") min_max_voltage: Float?,
              @Param("max_max_voltage") max_max_voltage: Float?,
              @Param("min_freq") min_freq: Float?,
              @Param("max_freq") max_freq: Float?,
              @Param("min_analog_inputs") min_analog_inputs: Int?,
              @Param("max_analog_inputs") max_analog_inputs: Int?,
              @Param("min_flashmemory") min_flashmemory: Int?,
              @Param("max_flashmemory") max_flashmemory: Int?,
              @Param("min_ram") min_ram: Int?,
              @Param("max_ram") max_ram: Int?) : Page<Platform>
}
