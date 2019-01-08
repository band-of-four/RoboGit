package org.robogit.domain

enum class Type(name: String) {
  SENSOR("Сенсор"),
  MECHANIC_DETAIL("Механическая деталь"),
  MOTOR("Мотор"),
  PLATFORM("Платформа"),
  CONTROLLER("Контроллер"),
  OTHER_RESOURCES("Прочие ресурсы");

  var typeName: String = name

  companion object {
    fun from(name: String): Type? {
      for (type in Type.values()) {
        if (type.typeName.toUpperCase() == name.toUpperCase())
          return type
      }

      throw IllegalArgumentException("No such type $name")
    }
  }
}
