package org.robogit.repository

import org.robogit.domain.MechanicDetail
import org.springframework.data.repository.CrudRepository

interface MechanicDetailRepository: CrudRepository<MechanicDetail, Int> {
}