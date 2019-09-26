package org.liara.data.type

import org.apache.commons.lang3.mutable.Mutable
import org.apache.commons.lang3.mutable.MutableObject
import spock.lang.Specification

import java.nio.ByteBuffer

abstract class DataTypeSpecification<T> extends Specification {
    private Random _random = new Random()

    def "#write and #read allows to write / read a value of the given type into / from a buffer"() {
        given: "an instance of the given type"
        final DataType<T> type = getType()

        and: "a data object"
        final ByteBuffer data = ByteBuffer.allocate(20 * type.bytes)

        and: "some values to write / read"
        final T[] values = getRandomValues(20)

        when: "we write all values into the given data object"
        for (final T value : values) {
            type.write(data, data.position(), value)
            data.position(data.position() + type.bytes)
        }

        then: "we expect to be able to read all written values"
        final Mutable<T> box = new MutableObject<>(null)

        for (int index = 0; index < values.length; ++index) {
            type.read(data, index * type.bytes, box)
            box.value == values[index]
        }
    }

    Random getRandom() {
        return _random
    }

    abstract DataType<T> getType()

    abstract T getRandomValue()

    T[] getRandomValues(int count) {
        final T[] result = (T[]) new Object[count]

        for (int index = 0; index < count; ++index) {
            result[index] = getRandomValue()
        }

        return result
    }
}
