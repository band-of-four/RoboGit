package org.robogit.repository

import org.robogit.domain.Information
import org.springframework.data.repository.CrudRepository

interface InformationRepository: CrudRepository<Information, Int> {
}