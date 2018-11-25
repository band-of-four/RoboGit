package org.robogit.repository

import org.robogit.domain.Interface
import org.springframework.data.repository.CrudRepository

interface InterfaceRepository: CrudRepository<Interface, Int> {

  /**
   * Finds instance of [Interface] with specified [name].
   * @param name is a name with which instances of [Interface] will be selected.
   * @return Instance of [Interface] if exists, null - otherwise.
   */
  fun findByName(name: String): Interface?
}
