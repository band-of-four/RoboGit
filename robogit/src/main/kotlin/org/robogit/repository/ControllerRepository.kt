package org.robogit.repository

import org.robogit.domain.Controller
import org.springframework.data.repository.CrudRepository

interface ControllerRepository: CrudRepository<Controller, Int> {
}