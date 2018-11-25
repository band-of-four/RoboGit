package org.robogit.repository

import org.robogit.domain.Controller
import org.robogit.domain.Platform
import org.springframework.data.repository.CrudRepository

interface PlatformRepository: CrudRepository<Platform, Int> {
  fun findByRamBetween(from: Int, to: Int): List<Platform>

  fun findByRamGreaterThan(ram: Int): List<Platform>

  fun findByRamLessThan(ram: Int): List<Platform>

  fun findByMinVoltageBetween(from: Float, to: Float): List<Platform>

  fun findByMinVoltageGreaterThan(minVoltage: Float): List<Platform>

  fun findByMinVoltageLessThan(minVoltage: Float): List<Platform>

  fun findByMaxVoltageBetween(from: Float, to: Float): List<Platform>

  fun findByMaxVoltageGreaterThan(maxVoltage: Float): List<Platform>

  fun findByMaxVoltageLessThan(maxVoltage: Float): List<Platform>

  fun findByController(controller: Controller): List<Platform>

  fun findByAnalogInputsBetween(from: Int, to: Int): List<Platform>

  fun findByAnalogInputsGreaterThan(analogInputs: Int): List<Platform>

  fun findByAnalogInputsLessThan(analogInputs: Int): List<Platform>

  fun findByFlashmemoryBetween(from: Int, to: Int): List<Platform>

  fun findByFlashmemoryGreaterThan(flashmemory: Int): List<Platform>

  fun findByFlashmemoryLessThan(flashmemory: Int): List<Platform>

  fun findByFreqBetween(from: Float, to: Float): List<Platform>

  fun findByFreqGreaterThan(freq: Float): List<Platform>

  fun findByFreqLessThan(freq: Float): List<Platform>
}
