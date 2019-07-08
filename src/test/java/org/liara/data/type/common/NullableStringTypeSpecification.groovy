package org.liara.data.type.common

import org.liara.data.type.NullableTypeSpecification
import org.liara.data.type.Type

import java.nio.charset.StandardCharsets

class NullableStringTypeSpecification
  extends NullableTypeSpecification<String>
{
  @Override
  Type getType () {
    return new NullableStringType(StandardCharsets.UTF_8, 100)
  }

  @Override
  String getRandomValue () {
    final int length = random.nextInt(60) + 20
    final char[] characters = new char[length]

    for (int index = 0; index < length; ++index) {
      characters[index] = (char) random.nextInt()
    }

    return new String(characters)
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == String.class
  }
}
