package org.robogit.repository

import org.robogit.domain.Platform
import org.springframework.data.repository.CrudRepository

interface PlatformRepository: CrudRepository<Platform, Int> {
}