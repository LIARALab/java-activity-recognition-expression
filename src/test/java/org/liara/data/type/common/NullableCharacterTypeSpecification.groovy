package org.liara.data.type.common

import org.liara.data.type.NullableTypeSpecification
import org.liara.data.type.Type

class NullableCharacterTypeSpecification
  extends NullableTypeSpecification<Character>
{
  @Override
  Type getType () {
    return new NullableCharacterType()
  }

  @Override
  Character getRandomValue () {
    return (char) random.nextInt()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == Character.class
  }
}
