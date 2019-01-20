package org.robogit.repository

import org.robogit.domain.MechanicDetail
import org.robogit.dto.MechanicDetailSumDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface MechanicDetailRepository: CrudRepository<MechanicDetail, Int> {

  /**
   * Finds all [MechanicDetail]s with specified [material].
   * @param material is a material with which [MechanicDetail]s will be selected.
   * @return List with results.
   */
  fun findByMaterial(material: String): List<MechanicDetail>

  /**
   * Возвращает все механические детали, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   */
  @Query("SELECT new org.robogit.dto.MechanicDetailSumDto(m, sum(p.amount)as s)  FROM MechanicDetail m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc")
  fun findPopular(): List<MechanicDetailSumDto?>?

  /**
   * Возвращает страницу механических деталей, отсортированные по популярности (количеству совершенных покупок)
   * и количество купленных товаров
   * @param pageable - номер страницы
   */
  @Query("SELECT new org.robogit.dto.MechanicDetailSumDto(m, sum(p.amount)as s)  FROM MechanicDetail m, ProductOrder p " +
          "JOIN p.information i " +
          "JOIN m.information i2 WHERE i.id=i2.id GROUP BY m.id ORDER BY s desc")
  fun findPagePopular(pageable: Pageable): Page<MechanicDetailSumDto?>?

  @Query("SELECT MechanicDetail FROM MechanicDetail WHERE i.id = :id")
  fun findMechanicDetailById(@Param("id") id: Int) : MechanicDetail
}
