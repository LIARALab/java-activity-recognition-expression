package org.liara.data.type.common

import org.liara.data.type.NullableTypeSpecification
import org.liara.data.type.Type

class NullableByteTypeSpecification
  extends NullableTypeSpecification<Byte>
{
  @Override
  Type getType () {
    return new NullableByteType()
  }

  @Override
  Byte getRandomValue () {
    return (byte) random.nextInt()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == Byte.class
  }
}
