package org.robogit.domain

import lombok.EqualsAndHashCode
import org.robogit.config.DatabaseConfig
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

/**
 * Класс, описывающий сущность Информации
 * хранит общую информацию характерную для всех продуктов
 * model - модель продукта
 * type - тип продукта:  Сенсор, Механическая деталь, Мотор, Платформа, Контроллер
 * price - цена за единицу товара
 * name - название товара
 * date_of_creation - дата создания товара
 * provider - производитель
 * description - описание продукта
 * amount - общее количество товаров
 * image - путь к изображению товара
 */
@Entity
@Table(name = "information", schema = DatabaseConfig.SCHEMA_NAME)
@EqualsAndHashCode(of = ["id"])
class Information : Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "information_gen")
  @SequenceGenerator(name = "information_gen", sequenceName = "information_id_seq")
  @Column
  var id: Int? = null

  @Column
  @Convert(converter = TypeConverter::class)
  var type: Type? = null

  @Column
  var model: String? = null

  @Column
  @NotBlank
  var name: String? = null

  @Column
  var image: String? = null

  @Column
  @Min(0)
  var price: Float? = null

  @Temporal(javax.persistence.TemporalType.TIMESTAMP)
  @Column
  var creationDate: Date? = null

  @Column
  var provider: String? = null

  @Column
  var description: String? = null

  @Column
  @Min(0)
  var amount: Int? = null
}
