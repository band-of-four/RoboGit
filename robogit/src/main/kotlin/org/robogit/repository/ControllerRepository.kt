package org.robogit.repository

import org.robogit.domain.Controller
import org.springframework.data.repository.CrudRepository

interface ControllerRepository : CrudRepository<Controller, Int> {
  fun findByRamBetween(from: Int, to: Int): List<Controller>

  fun findByRamGreaterThan(ram: Int): List<Controller>

  fun findByRamLessThan(ram: Int): List<Controller>

  fun findByMinVoltageBetween(from: Float, to: Float): List<Controller>

  fun findByMinVoltageGreaterThan(minVoltage: Float): List<Controller>

  fun findByMinVoltageLessThan(minVoltage: Float): List<Controller>

  fun findByMaxVoltageBetween(from: Float, to: Float): List<Controller>

  fun findByMaxVoltageGreaterThan(maxVoltage: Float): List<Controller>

  fun findByMaxVoltageLessThan(maxVoltage: Float): List<Controller>

  fun findByAnalogInputsBetween(from: Int, to: Int): List<Controller>

  fun findByAnalogInputsGreaterThan(analogInputs: Int): List<Controller>

  fun findByAnalogInputsLessThan(analogInputs: Int): List<Controller>
}
