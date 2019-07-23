package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes
import org.liara.support.generic.Generic
import org.liara.support.generic.Generics

class CharacterDataTypeSpecification
  extends DataTypeSpecification<Character>
{
  @Override
  DataType<Byte> getType () {
    return DataTypes.CHARACTER
  }

  @Override
  Generic<Byte> getExpextedGeneric () {
    return Generics.CHARACTER
  }

  @Override
  Character getRandomValue () {
    return (char) random.nextInt()
  }
}
