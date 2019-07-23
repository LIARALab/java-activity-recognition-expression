package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes
import org.liara.support.generic.Generic
import org.liara.support.generic.Generics

class FloatDataTypeSpecification
  extends DataTypeSpecification<Float>
{
  @Override
  DataType<Float> getType () {
    return DataTypes.FLOAT
  }

  @Override
  Generic<Float> getExpextedGeneric () {
    return Generics.FLOAT
  }

  @Override
  Float getRandomValue () {
    return random.nextFloat()
  }
}
