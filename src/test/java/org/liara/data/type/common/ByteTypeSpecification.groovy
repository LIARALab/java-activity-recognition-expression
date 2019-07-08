package org.liara.data.type.common

import org.liara.data.type.Type
import org.liara.data.type.TypeSpecification

class ByteTypeSpecification
  extends TypeSpecification<Byte>
{
  @Override
  Type getType () {
    return new ByteType()
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
