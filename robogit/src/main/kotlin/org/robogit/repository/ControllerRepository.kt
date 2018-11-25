package org.robogit.repository

import org.robogit.domain.Controller
import org.robogit.domain.Interface
import org.springframework.data.repository.CrudRepository

interface ControllerRepository : CrudRepository<Controller, Int> {

  /**
   * Finds all [Controller]s which [Controller.ram] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByRamBetween(from: Int, to: Int): List<Controller>

  /**
   * Finds all [Controller]s which [Controller.ram] is greater than [ram].
   * @param ram is the left search boundary.
   * @return List with results.
   */
  fun findByRamGreaterThan(ram: Int): List<Controller>

  /**
   * Finds all [Controller]s which [Controller.ram] is less than [ram].
   * @param ram is the right search boundary.
   * @return List with results.
   */
  fun findByRamLessThan(ram: Int): List<Controller>

  /**
   * Finds all [Controller]s which [Controller.minVoltage] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByMinVoltageBetween(from: Float, to: Float): List<Controller>

  /**
   * Finds all [Controller]s which [Controller.minVoltage] is greater than [minVoltage].
   * @param minVoltage is the left search boundary.
   * @return List with results.
   */
  fun findByMinVoltageGreaterThan(minVoltage: Float): List<Controller>

  /**
   * Finds all [Controller]s which [Controller.minVoltage] is less than [minVoltage].
   * @param minVoltage is the right search boundary.
   * @return List with results.
   */
  fun findByMinVoltageLessThan(minVoltage: Float): List<Controller>

  /**
   * Finds all [Controller]s which [Controller.maxVoltage] is between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageBetween(from: Float, to: Float): List<Controller>

  /**
   * Finds all [Controller]s which [Controller.maxVoltage] is greater than [maxVoltage].
   * @param maxVoltage is the left search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageGreaterThan(maxVoltage: Float): List<Controller>

  /**
   * Finds all [Controller]s which [Controller.maxVoltage] is less than [maxVoltage].
   * @param maxVoltage is the right search boundary.
   * @return List with results.
   */
  fun findByMaxVoltageLessThan(maxVoltage: Float): List<Controller>

  /**
   * Finds all [Controller]s with [Controller.analogInputs] (i.e. count of analog inputs) between [from] and [to].
   * @param from is the left search boundary.
   * @param to is the right search boundary.
   * @return List with results.
   */
  fun findByAnalogInputsBetween(from: Int, to: Int): List<Controller>

  /**
   * Finds all [Controller]s with [Controller.analogInputs] (i.e. count of analog inputs) greater than [analogInputs].
   * @param analogInputs is the left search boundary.
   * @return List with results.
   */
  fun findByAnalogInputsGreaterThan(analogInputs: Int): List<Controller>

  /**
   * Finds all [Controller]s with [Controller.analogInputs] (i.e. count of analog inputs) less than [analogInputs].
   * @param analogInputs is the right search boundary.
   * @return List with results.
   */
  fun findByAnalogInputsLessThan(analogInputs: Int): List<Controller>

  /**
   * Finds all [Controller]s with specified [Controller.controllerInterface].
   * @param controllerInterface is an [Interface] with which [Controller]s will be selected.
   * @return List with results.
   */
  fun findByControllerInterface(controllerInterface: Interface): List<Controller>
}
