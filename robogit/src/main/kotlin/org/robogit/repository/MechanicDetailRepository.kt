package org.robogit.repository

import org.robogit.domain.MechanicDetail
import org.springframework.data.repository.CrudRepository

interface MechanicDetailRepository: CrudRepository<MechanicDetail, Int> {

  /**
   * Finds all [MechanicDetail]s with specified [material].
   * @param material is a material with which [MechanicDetail]s will be selected.
   * @return List with results.
   */
  fun findByMaterial(material: String): List<MechanicDetail>
}
