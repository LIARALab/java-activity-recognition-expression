package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes
import org.liara.support.generic.Generic
import org.liara.support.generic.Generics

class LongDataTypeSpecification
  extends DataTypeSpecification<Long>
{
  @Override
  DataType<Long> getType () {
    return DataTypes.LONG
  }

  @Override
  Generic<Long> getExpextedGeneric () {
    return Generics.LONG
  }

  @Override
  Long getRandomValue () {
    return random.nextLong()
  }
}
