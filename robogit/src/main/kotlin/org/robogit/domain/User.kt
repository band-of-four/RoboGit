package org.robogit.domain

import org.robogit.config.DatabaseConfig
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Класс, описывающий сущность польщователя
 * login - логин пользователя в системе
 * password - пароль пользователя в системе
 * telegram_id - id в телеграме
 */
@Entity
@Table(name = "users", schema = DatabaseConfig.SCHEMA_NAME)
class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
  @SequenceGenerator(name = "user_gen", sequenceName = "user_id_seq")
  @Column
  var id: Int? = null

  @NotBlank
  @Column
  var login: String? = null

  @NotBlank
  @Column
  var password: String? = null

  @Column
  var telegramId: String? = null

  @OneToMany(mappedBy = "user")
  var orders: Set<Order> = HashSet()

  @OneToMany(mappedBy = "user")
  var repositories: Set<Repository> = HashSet()

  @OneToMany(mappedBy = "user")
  var stars: Set<Star> = HashSet()

  @OneToMany(mappedBy = "user")
  var products: Set<ProductUser> = HashSet()
}
