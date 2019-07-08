package org.liara.data.type

import org.liara.data.Data
import org.liara.data.cursor.Cursor
import org.liara.data.cursor.StaticCursor
import spock.lang.Specification

abstract class TypeSpecification<T> extends Specification {
  private Random _random = new Random()

  def "#write and #read allows to write / read a value of the given type into / from a buffer" () {
    given: "an instance of the given type"
    final Type type = getType()

    and: "a data object"
    final Data data = new Data(20 * type.size)

    and: "some values to write / read"
    final T[] values = getRandomValues(20)

    and: "a cursor over the given data object"
    final Cursor cursor = new StaticCursor(data)

    when: "we write all values into the given data object"
    for (final T value : values) {
      type.write(value, cursor)
    }

    then: "we expect the cursor to moved at the end of the data object"
    cursor.offset == 20 * type.size

    and: "we expect to be able to read all written values"
    cursor.setOffset(0)

    for (int index = 0; index < values.length; ++index) {
      type.read(cursor) == values[index]
    }
  }

  Random getRandom () {
    return _random
  }

  abstract Type getType ()
  abstract T getRandomValue ()

  T[] getRandomValues (int count) {
    final T[] result = (T[]) new Object[count]

    for (int index = 0; index < count; ++index) {
      result[index] = getRandomValue()
    }

    return result
  }
}
