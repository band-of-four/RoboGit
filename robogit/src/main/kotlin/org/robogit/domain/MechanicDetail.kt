package org.robogit.domain

import org.robogit.config.DatabaseConfig
import javax.persistence.*

/**
 * Класс, описывающий сущность Мехонической детальки
 * material - материал из которого сделана деталь
 */
@Entity
@Table(name = "mechanic_details", schema = DatabaseConfig.SCHEMA_NAME)
class MechanicDetail {
  @Id
  @Column
  var id: Int? = null

  @OneToOne
  @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
  var information: Information? = null

  @Column
  var material: String? = null
}
