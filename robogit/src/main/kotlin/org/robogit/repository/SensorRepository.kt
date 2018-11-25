package org.robogit.repository

import org.robogit.domain.Sensor
import org.springframework.data.repository.CrudRepository

interface SensorRepository: CrudRepository<Sensor, Int> {
}