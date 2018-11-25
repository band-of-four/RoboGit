package org.robogit.domain

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class TypeConverter : AttributeConverter<Type, String> {

  override fun convertToDatabaseColumn(attribute: Type): String {
    return attribute.typeName
  }

  override fun convertToEntityAttribute(dbData: String): Type? {
    return Type.from(dbData)
  }
}
